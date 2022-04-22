package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.LibraryApplication;
import com.netcracker.ageev.library.dto.ImageDTO;
import com.netcracker.ageev.library.exception.DataNotFoundException;
import com.netcracker.ageev.library.model.Image;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.repository.ImageRepository;
import com.netcracker.ageev.library.repository.books.BooksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {

    private static final Logger LOG = LoggerFactory.getLogger(ImageService.class);
    private final BooksRepository booksRepository;
    private final ImageRepository imageRepository;
    private final UsersService usersService;

    @Autowired
    public ImageService(ImageRepository imageRepository, BooksRepository booksRepository, UsersService usersService) {
        this.imageRepository = imageRepository;
        this.booksRepository = booksRepository;
        this.usersService = usersService;
    }

    public ImageDTO getBookImage(String idImage) throws IOException {
        long id = Long.parseLong(idImage);
        String image = null;
        try {
            image = imageRepository.findByBooksId(id).orElseThrow(() -> new DataNotFoundException("message")).getFileName();
        } catch (DataNotFoundException e) {
            LOG.info("Image book not found. id Image book:" + idImage);
            image = "/not_found.jpg";
            e.printStackTrace();
        }
        Path path = Paths.get(System.getProperty("user.dir") + "/image/books/" + image);
        return getImageByPathAndConvert(path,"bookId: "+idImage);

    }

    public ImageDTO getProfileImage(Principal principal) throws IOException {
        Users users = usersService.getUserByPrincipal(principal);
        String image = null;
        try {
            image = users.getId() + "/" + imageRepository.findByUsersId(users.getId()).orElseThrow(() -> new DataNotFoundException("message")).getFileName();
        } catch (DataNotFoundException e) {
            image = "/not_found.jpg";
            e.printStackTrace();
        }
        Path path = Paths.get(System.getProperty("user.dir") + "/image/users/" + image);
        return getImageByPathAndConvert(path,"userId: "+users.getId());

    }

    private ImageDTO getImageByPathAndConvert(Path path, String fileString) throws IOException {
        ImageDTO imageDTO = new ImageDTO();
        BufferedImage img;
//        LOG.debug(path.toString());
        if (Files.exists(path)) {
            img = ImageIO.read(new File(path.toString()));
        } else {
            LOG.warn("the file is in the database but not on disk: " + fileString);
            img = ImageIO.read(new File(System.getProperty("user.dir") + "/image/not_found.jpg"));
        }
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", bao);
        imageDTO.setBytes(bao.toByteArray());
        return imageDTO;
    }


    public Image uploadImageBooks(MultipartFile file, String id) throws IOException {
        Image image = new Image();
        File uploadDir = new File(System.getProperty("user.dir") + "/image/books");
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
        File uploadDir = new File(System.getProperty("user.dir") + "/image/users/" + users.getId());
        boolean isDirectoryCreated = uploadDir.exists() || uploadDir.mkdirs();
        if (!isDirectoryCreated) {
            isDirectoryCreated = uploadDir.mkdirs();
            LOG.warn("failed to create Directory: " + uploadDir);
        }
        if (isDirectoryCreated) {
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadDir + "/" + resultFileName));
            Optional<Image> image1 = imageRepository.findByUsersId(users.getId());
            if (image1.isPresent()) {
                File oldImageProfile = new File(uploadDir + "/" + image1.get().getFileName());
                if (oldImageProfile.delete()) {
                    LOG.info("old image profile delete " + users.getEmail());
                } else {
                    LOG.info("file not found " + users.getEmail());
                }

                Image image2 = image1.get();
                image2.setFileName(resultFileName);
                image2.setUsersId(users.getId());
                LOG.info("Update image. New name: " + image2.getFileName());
                return imageRepository.save(image2);
            }
            image.setFileName(resultFileName);
            image.setUsersId(users.getId());
            LOG.info("new Upload image" + image.getFileName());
        }
        return imageRepository.save(image);
    }


}
