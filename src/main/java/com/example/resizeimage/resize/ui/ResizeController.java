package com.example.resizeimage.resize.ui;

import com.example.resizeimage.resize.service.ResizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ResizeController {

    private final ResizeService resizeService;

    @PostMapping("/resize")
    public ResponseEntity<String> resizeImage(@RequestParam("image")MultipartFile image,
                                              @RequestParam Integer maxLength) {
        String resizedImageName = resizeService.resizeImage(image, maxLength);
        return ResponseEntity.ok(resizedImageName);
    }
}
