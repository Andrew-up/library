package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.exception.ErrorMessage;
import com.netcracker.ageev.library.model.Image;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.repository.ImageRepository;
import com.netcracker.ageev.library.repository.books.BooksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.UUID;

@Service
public class ImageService {

    public static final Logger LOG = LoggerFactory.getLogger(ImageService.class);

    private final BooksRepository booksRepository;
    private final ImageRepository imageRepository;
    private final UsersService usersService;

    @Autowired
    public ImageService(ImageRepository imageRepository, BooksRepository booksRepository,UsersService usersService) {
        this.imageRepository = imageRepository;
        this.booksRepository = booksRepository;
        this.usersService = usersService;
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
    public String getBookImage(String idImage) {
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

    public String getProfileImage(Principal principal) {
        Users users = usersService.getUserByPrincipal(principal);
        String image = null;
        try {
            image = users.getId()+"/"+imageRepository.findByUsersId(users.getId()).orElseThrow(() -> new NullPointerException("message")).getFileName();
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


    public Image uploadImageUserProfile(MultipartFile file, Principal principal) throws IOException {
        Users users = usersService.getUserByPrincipal(principal);
        Image image = new Image();
        File uploadDir = new File(uploadPath + "/users/"+users.getId());
        boolean isDirectoryCreated = uploadDir.exists() || uploadDir.mkdirs();
        if (!isDirectoryCreated) {
            isDirectoryCreated = uploadDir.mkdirs();
        }
        if (isDirectoryCreated) {
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadDir + "/" + resultFileName));
            Image image1 = null;
            try {
                image1 = imageRepository.findByUsersId(users.getId()).orElseThrow(() -> new FileNotFoundException("Not found"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if(image1!=null){
                File oldImageProfile = new File(uploadDir+"/"+image1.getFileName());
                if(oldImageProfile.delete()){
                    LOG.info("Старый файл удален");
                }
                else {
                    LOG.info("Файл не найден");
                }

                image1.setFileName(resultFileName);
                image1.setUsersId(users.getId());
                LOG.info("update image");
                LOG.info("Image: "+image1.getFileName());
                return imageRepository.save(image1);
            }
            image.setFileName(resultFileName);
            image.setUsersId(users.getId());
            LOG.info("new Upload image");
        }
        return imageRepository.save(image);
    }


}
