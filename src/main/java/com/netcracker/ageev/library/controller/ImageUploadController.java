package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.ImageDTO;
import com.netcracker.ageev.library.payload.responce.MessageResponse;
import com.netcracker.ageev.library.repository.ImageRepository;
import com.netcracker.ageev.library.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.PermitAll;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/image")
@CrossOrigin
@PermitAll()
public class ImageUploadController {
    private static final Logger LOG = LoggerFactory.getLogger(ImageUploadController.class);
    private final ImageService imageService;

    @Autowired
    public ImageUploadController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/book/{id}")
    public ResponseEntity<MessageResponse> uploadImageBooks(
            @RequestParam("file") MultipartFile file, @PathVariable("id") String id) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            imageService.uploadImageBooks(file, id);
            return ResponseEntity.ok(new MessageResponse("Image uploaded successfully"));
        }
        LOG.info("error upload image book id: "+ id);
        return ResponseEntity.ok(new MessageResponse("Ошибка загрузки, возможно файл не добавлен"));
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.GET, produces = "image/jpg")
    public @ResponseBody
    byte[] getFile(@PathVariable String id) throws IOException {
        ImageDTO imageDTO = imageService.getBookImage(id);
        return imageDTO.getBytes();
    }

    @PostMapping("/user")
    public ResponseEntity<MessageResponse> uploadImageProfileUser(
            @RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            imageService.uploadImageUserProfile(file, principal);
            return ResponseEntity.ok(new MessageResponse("Image uploaded successfully"));
        }
        return ResponseEntity.ok(new MessageResponse("Ошибка загрузки, возможно файл не добавлен"));
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = "image/jpg")
    public @ResponseBody
    byte[] getFile(Principal principal) throws IOException {
        ImageDTO imageDTO = imageService.getProfileImage(principal);
        return imageDTO.getBytes();
    }

}
