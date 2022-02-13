package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.model.Image;
import com.netcracker.ageev.library.model.books.Books;
import com.netcracker.ageev.library.repository.ImageRepository;
import com.netcracker.ageev.library.repository.books.BooksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {

    public static final Logger LOG = LoggerFactory.getLogger(ImageService.class);

    private final BooksRepository booksRepository;

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository, BooksRepository booksRepository) {
        this.imageRepository = imageRepository;
        this.booksRepository = booksRepository;
    }

    @Value("${upload.path}")
    private String uploadPath;

    public Image uploadImage(MultipartFile file) throws IOException {
        Image image = new Image();
        File uploadDir = new File(uploadPath);
        boolean isDirectoryCreated = uploadDir.exists() || uploadDir.mkdirs();
        if (!isDirectoryCreated) {
            isDirectoryCreated = uploadDir.mkdirs();
        }
        if(isDirectoryCreated){
            String uuidFile= UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            image.setFileName(resultFileName);
            LOG.info("Upload image");
            file.transferTo(new File(uploadPath+ "/" +resultFileName));
        }
        return imageRepository.save(image);
    }

    // TODO: Добавить свое исключение
    public String getImage(String idImage) {
        long id = Long.parseLong(idImage);
        String image = null;
        try {
            image = imageRepository.findByBooksId(id).orElseThrow(() -> new NullPointerException("message")).getFileName();
        } catch (NullPointerException e) {
            image = "/not_found.jpg";
            e.printStackTrace();
        }
        return image;

    }


    public Image uploadImageBooks(MultipartFile file, String id) throws IOException {

        Image image = new Image();
        File uploadDir = new File(uploadPath + "/books");
        boolean isDirectoryCreated = uploadDir.exists() || uploadDir.mkdirs();
        if (!isDirectoryCreated) {
            isDirectoryCreated = uploadDir.mkdirs();
        }
        if (isDirectoryCreated) {
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            image.setFileName(resultFileName);
            image.setBooksId(Long.parseLong(id));
            LOG.info("Upload image");
            file.transferTo(new File(uploadDir + "/" + resultFileName));
        }
        return imageRepository.save(image);
    }


    public Image getBooksImage(Long booksId) {
        Image booksImage = imageRepository.findByBooksId(booksId)
                .orElseThrow(() -> new NullPointerException("Image connot found for post "));
        if (!ObjectUtils.isEmpty(booksImage)) {
            return booksImage;
        }
        return booksImage;
    }


}
