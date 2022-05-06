package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.BooksDTO;
import com.netcracker.ageev.library.exception.DataNotFoundException;
import com.netcracker.ageev.library.model.books.Authors;
import com.netcracker.ageev.library.model.books.Books;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.repository.books.BooksRepository;
import com.netcracker.ageev.library.repository.users.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BooksService {

    public static final Logger LOG = LoggerFactory.getLogger(BooksService.class);
    private final BooksRepository booksRepository;
    private final UsersRepository usersRepository;
    private final AuthorsService authorsService;
    private final BookGenresService bookGenresService;
    private final SeriesService bookSeriesService;
    private final TranslationService translationService;
    private final PublisherService publisherService;
    private final CoverBookService coverBookService;
    private final AgeLimitService ageLimitService;
    private final EditionLanguageService editionLanguageService;
    private final PriceService priceService;


    @Autowired
    public BooksService(BooksRepository booksRepository,
                        UsersRepository usersRepository,
                        AuthorsService authorsService,
                        BookGenresService bookGenresService,
                        SeriesService bookSeriesService,
                        TranslationService translationService,
                        PublisherService publisherService,
                        CoverBookService coverBookService,
                        AgeLimitService ageLimitService,
                        EditionLanguageService editionLanguageService,
                        PriceService priceService) {
        this.booksRepository = booksRepository;
        this.usersRepository = usersRepository;
        this.authorsService = authorsService;
        this.bookGenresService = bookGenresService;
        this.bookSeriesService = bookSeriesService;
        this.translationService = translationService;
        this.publisherService = publisherService;
        this.coverBookService = coverBookService;
        this.ageLimitService = ageLimitService;
        this.editionLanguageService = editionLanguageService;
        this.priceService = priceService;
    }


    public List<Books> getAllBooks() {
        return booksRepository.findAllByOrderById();
    }
    public Page<Books> getNewsBook(Pageable pageable) {
        return booksRepository.findAllByOrderByIdDesc(pageable);
    }
    public Page<Books> getMaxRent(Pageable pageable) {
        Page<Books> page =  booksRepository.findFrequentRent(pageable);
        return page;
    }

    public List<Books> getAllBookByAuthorsId(Integer id) {
        Authors authors = authorsService.getAuthorsById(id);
        return booksRepository.findAllByAuthors(authors);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooksService that = (BooksService) o;
        return Objects.equals(booksRepository, that.booksRepository) && Objects.equals(usersRepository, that.usersRepository) && Objects.equals(authorsService, that.authorsService) && Objects.equals(bookGenresService, that.bookGenresService) && Objects.equals(bookSeriesService, that.bookSeriesService) && Objects.equals(translationService, that.translationService) && Objects.equals(publisherService, that.publisherService) && Objects.equals(coverBookService, that.coverBookService) && Objects.equals(ageLimitService, that.ageLimitService) && Objects.equals(editionLanguageService, that.editionLanguageService) && Objects.equals(priceService, that.priceService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booksRepository, usersRepository, authorsService, bookGenresService, bookSeriesService, translationService, publisherService, coverBookService, ageLimitService, editionLanguageService, priceService);
    }

    public List<Books> getAllBooksByInputSearch(String inputSearch, String typeSearch) {
        List<Books> booksListSearch;
        System.out.println("Область поиска: "+typeSearch);
        if (typeSearch.equals("booktitle")) {
            booksListSearch = booksRepository.findAllByBookTitle(inputSearch);
        }
         else if (typeSearch.equals("authors")) {
            booksListSearch = booksRepository.findAllByAuthors(inputSearch);
        }
        else if (typeSearch.equals("genres")) {
            booksListSearch = booksRepository.findAllByGenres(inputSearch);
        }
        else if (typeSearch.equals("publisher")) {
            booksListSearch = booksRepository.findAllByPublisher(inputSearch);
        }
        else if (typeSearch.equals("series")) {
            booksListSearch = booksRepository.findAllBySeries(inputSearch);
        }
        else {
            booksListSearch = new ArrayList<>();
        }

        return booksListSearch;
    }

    public Page<Books> getAllBooksByNumberPage(Pageable pageable) {
        return booksRepository.findAllByOrderById(pageable);
    }

    public Books getBookUserById(Long id) {
        Books books;
        try {
            books = booksRepository.findBooksById(id).orElseThrow(() -> new DataNotFoundException("not found. getBookUserById: "+ id));
            return books;
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return new Books();
        }

    }

    public Books createBook(BooksDTO booksDTO, Principal principal) {
        Users users = getUserByPrincipal(principal);
        Books books = new Books();
        ArrayList<String> arrayListError = isBookCorrect(booksDTO);
        if (!ObjectUtils.isEmpty(arrayListError)) {
            books.setBookTitle(arrayListError.toString());
            return books;
        }
        books.setBookTitle(booksDTO.getBookTitle()); // название
        books.setAuthors(authorsService.getAuthorsById(booksDTO.getAuthors())); // Автор
        books.setGenreCode(bookGenresService.getGenresById(booksDTO.getGenreCode())); // Жанр
        books.setReleaseDate(booksDTO.getBookReleaseDate()); // Дата выхода книги
        books.setPublisherId(publisherService.getPublisherById(booksDTO.getPublisherId())); // Издательство
        books.setNumberPages(booksDTO.getNumberPages()); // кол-во страниц
        books.setCoverId(coverBookService.getCoverBookById(booksDTO.getCoverId())); // Тип обложки
        try {
            books.setSeries(bookSeriesService.getSeriesById(booksDTO.getBookSeries())); // Серия
        } catch (Exception e) {
            LOG.error(String.valueOf(e.getMessage()));
            e.printStackTrace();
        }
        books.setISBN(booksDTO.getNameISBN());   //ISBN
        books.setAgeLimitCode(ageLimitService.getAgeLimitById(booksDTO.getAgeLimitCode()));  // Возрастное ограничение
        books.setLanguageId(editionLanguageService.getEditionLanguageById(booksDTO.getLanguageId()));// Язык издания
        try {
            books.setTranslation(translationService.getTranslationById(booksDTO.getTranslationId())); // Автор перевода
        } catch (Exception e) {
            LOG.error(String.valueOf(e.getMessage()));
            e.printStackTrace();
        }
        books.setPrice(priceService.getPriceById(booksDTO.getPriceId()));
        books.setCountBooks(booksDTO.getCountBooks());
        return booksRepository.save(books);
    }

    public Users getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return usersRepository.findUsersByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username" + username));
    }

    public Books getBookById(Long id) {
        try {
            return booksRepository.findBooksById(id).orElseThrow(() -> new DataNotFoundException("not found. getBookById: "+id));
        } catch (DataNotFoundException e) {
            return null;
        }
    }

    private ArrayList<String> isBookCorrect(BooksDTO booksDTO) {

        ArrayList<String> listError = new ArrayList<>();
        if (booksDTO.getBookTitle().equals("null")) {
            listError.add("Имя книги не корректно");
        }
        if (booksDTO.getAuthors() == 0) {
            listError.add("Автор не корректен");
        }
        if (booksDTO.getGenreCode() == 0) {
            listError.add("Жанр не корректен");
        }
        if (booksDTO.getBookReleaseDate().equals("null")) {
            listError.add("Дата релиза не корректна");
        }
        if (booksDTO.getPublisherId() == 0) {
            listError.add("Издательство не корректен");
        }
        if (booksDTO.getNumberPages() == null) {
            listError.add("кол-во страниц не корректно");
        }
        if (booksDTO.getNameISBN().equals("null")) {
            listError.add("ISBN не корректен");
        }
        if (booksDTO.getAgeLimitCode() == 0) {
            listError.add("Возрастное ограничение не корректено");
        }
        if (booksDTO.getLanguageId() == 0) {
            listError.add("Язык издания не корректен");
        }
//        if (booksDTO.getTranslationId() == 0) {
//            listError.add("Автор перевода не корректен");
//        }
        if (booksDTO.getPriceId() == 0) {
            listError.add("Стоимость аренды не корректна");
        }
        return listError;
    }


}
