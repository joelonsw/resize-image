package com.example.resizeimage.image.ui;

import com.example.resizeimage.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> getFile(@PathVariable String fileName) {
        byte[] file = imageService.getFile(fileName);
        return ImageContentTypeFactory.generateResponseEntity(fileName, file);
    }
}
