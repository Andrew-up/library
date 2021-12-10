package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.model.Image;
import com.netcracker.ageev.library.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {

    public static final Logger LOG = LoggerFactory.getLogger(ImageService.class);

    private final ImageRepository imageRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }
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
    public String getImage(String idImage)   {
        long id = Long.parseLong(idImage);
        String image = imageRepository.findById(id).orElseThrow(() -> new NullPointerException("message")).getFileName();
        return image;

    }

}
