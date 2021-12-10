package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.model.Image;
import com.netcracker.ageev.library.payload.responce.MessageResponse;
import com.netcracker.ageev.library.repository.ImageRepository;
import com.netcracker.ageev.library.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.PermitAll;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;

@RestController
@RequestMapping("/api/image")
@CrossOrigin
@PermitAll()
public class FileUploadController {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("/book")
    public ResponseEntity<MessageResponse> uploadImage(
            @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            imageService.uploadImage(file);
            return ResponseEntity.ok(new MessageResponse("Image uploaded successfully"));
        }
        return ResponseEntity.ok(new MessageResponse("Ошибка загрузки, возможно файл не добавлен"));
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.GET, produces = "image/jpg")
    public @ResponseBody byte[] getFile(@PathVariable String id)  {
        try {
            // Retrieve image from the classpath.
//            InputStream is = getClass().getClassLoader().getResourceAsStream("image/test.jpg");
            String image = "/"+imageService.getImage(id);
          File file = new File(uploadPath+image);
            System.out.println(file.toString());
            // Prepare buffered image.
            BufferedImage img = ImageIO.read(file);
            // Create a byte array output stream.
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            // Write to output stream
            ImageIO.write(img, "jpg", bao);
            return bao.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
