package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.payload.responce.MessageResponse;
import com.netcracker.ageev.library.repository.ImageRepository;
import com.netcracker.ageev.library.service.ImageService;
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
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;

    @Value("${upload.path}")
    private String uploadPath;

//    @PostMapping("/book")
//    public ResponseEntity<MessageResponse> uploadImage(
//            @RequestParam("file") MultipartFile file) throws IOException {
//        if (file != null && !file.getOriginalFilename().isEmpty()) {
//            imageService.uploadImage(file);
//            return ResponseEntity.ok(new MessageResponse("Image uploaded successfully"));
//        }
//        return ResponseEntity.ok(new MessageResponse("Ошибка загрузки, возможно файл не добавлен"));
//    }

    @PostMapping("/book/{id}")
    public ResponseEntity<MessageResponse> uploadImageBooks(
            @RequestParam("file") MultipartFile file, @PathVariable("id") String id) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            imageService.uploadImageBooks(file,id);
            return ResponseEntity.ok(new MessageResponse("Image uploaded successfully"));
        }
        return ResponseEntity.ok(new MessageResponse("Ошибка загрузки, возможно файл не добавлен"));
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.GET, produces = "image/jpg")
    public @ResponseBody byte[] getFile(@PathVariable String id)  {
        try {
            // Retrieve image from the classpath.
//            InputStream is = getClass().getClassLoader().getResourceAsStream("image/test.jpg");

            String image = "/books/"+imageService.getBookImage(id);
            File file = new File(uploadPath+image);
//            System.out.println(file.toString());
            BufferedImage img = ImageIO.read(file);
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            if(img == null){
                System.out.println("Картинка не найдена");
            }
            ImageIO.write(img, "jpg", bao);
            return bao.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/user")
    public ResponseEntity<MessageResponse> uploadImageProfileUser(
            @RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            imageService.uploadImageUserProfile(file,principal);
            return ResponseEntity.ok(new MessageResponse("Image uploaded successfully"));
        }
        return ResponseEntity.ok(new MessageResponse("Ошибка загрузки, возможно файл не добавлен"));
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = "image/jpg")
    public @ResponseBody byte[] getFile(Principal principal)  {
        try {
            System.out.println("GET USER: "+imageService.getProfileImage(principal));
            String image = "/users/"+imageService.getProfileImage(principal);
            File file = new File(uploadPath+image);
            System.out.println(file.toString());
            BufferedImage img = ImageIO.read(file);
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            if(img == null){
                System.out.println("Картинка не найдена");
            }
            ImageIO.write(img, "jpg", bao);
            return bao.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



//    @RequestMapping(value = "{booksId}/image", method = RequestMethod.GET, produces = "image/jpg")
//    public @ResponseBody byte[] getPostImage(@PathVariable String booksId)  {
//        try {
//            // Retrieve image from the classpath.
////            InputStream is = getClass().getClassLoader().getResourceAsStream("image/test.jpg");
//            String image = "/"+imageService.getImage(booksId);
//            File file = new File(uploadPath+"/books"+image);
//            System.out.println(file.toString());
//            BufferedImage img = ImageIO.read(file);
//            ByteArrayOutputStream bao = new ByteArrayOutputStream();
//
//            ImageIO.write(img, "jpg", bao);
//
//            return bao.toByteArray();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
