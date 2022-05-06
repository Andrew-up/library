package com.netcracker.ageev.library.service;


import com.netcracker.ageev.library.dto.PriceRentDTO;
import com.netcracker.ageev.library.exception.DataNotFoundException;
import com.netcracker.ageev.library.model.books.Price;
import com.netcracker.ageev.library.repository.books.BookRentRepository;
import com.netcracker.ageev.library.repository.books.PriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PriceService {

    public static final Logger LOG = LoggerFactory.getLogger(PriceService.class);

    private final BookRentRepository bookRentRepository;
    private final PriceRepository priceRepository;
    private final UsersService usersService;

    @Autowired
    public PriceService(BookRentRepository bookRentRepository,
                        PriceRepository priceRepository,
                        UsersService usersService) {
        this.bookRentRepository = bookRentRepository;
        this.priceRepository = priceRepository;
        this.usersService = usersService;
    }

    public List<Price> getAllPrice() {
        return priceRepository.findAllByOrderById();
    }

    public Price getPriceById(Integer id) {
        try {
            return priceRepository.findPriceById(id).orElseThrow(() -> new DataNotFoundException("not found. getPriceById: " + id));
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String createPrice(PriceRentDTO priceRentDTO, Principal principal) {

        Price price = new Price();
        ArrayList<String> arrayListError = isPriceBookCorrect(priceRentDTO);
        if (!ObjectUtils.isEmpty(arrayListError)) {
            return arrayListError.toString();
        }

        return savePriceBook(priceRentDTO, price); // ошибка при добавлении в бд; -id -2000

    }

    private ArrayList<String> isPriceBookCorrect(PriceRentDTO priceRentDTO) {
        ArrayList<String> listError = new ArrayList<>();
        String regex = "^[1-9]+[0-9]*(\\.[1-9]*)?$";
        boolean result = priceRentDTO.getPriceName().matches(regex);
        if (!result) {
            LOG.info("The expression did not pass the record format check. isPriceBookCorrect :" + priceRentDTO.getPriceName());
            listError.add("Выражение не прошло проверку по формату записи");
        }
        return listError;
    }

    private String savePriceBook(PriceRentDTO priceRentDTO, Price price) {
        try {
            price.setPriceRent(Double.parseDouble(priceRentDTO.getPriceName()));
            priceRepository.save(price);
            return priceRentDTO.getPriceName();
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return "-2000";
        }
    }

    public String updatePrice(PriceRentDTO priceRentDTO, Principal principal) {

        Price price = priceRepository.findPriceById(priceRentDTO.getId()).orElseThrow(() -> new UsernameNotFoundException("Price not found"));
        ArrayList<String> arrayListError = isPriceBookCorrect(priceRentDTO);
        if (!ObjectUtils.isEmpty(arrayListError)) {
            return arrayListError.toString();
        }
        return savePriceBook(priceRentDTO, price); // ошибка при добавлении в бд; -id -2000
    }

    public String deletePrice(Integer priceId, Principal principal) {

        Optional<Price> delete = priceRepository.findPriceById(priceId);
        delete.ifPresent(priceRepository::delete);
        if (!delete.isPresent()){
            throw  new DataNotFoundException("Genre not found. deletePrice: " +priceId);
        }
        else {
            return "Цена аренды с id: " + priceId + " удалена";
        }


    }


}
