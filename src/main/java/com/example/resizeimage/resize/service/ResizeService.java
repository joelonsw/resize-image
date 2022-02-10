package com.example.resizeimage.resize.service;

import com.example.resizeimage.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ResizeService {

    private final ImageService imageService;

    public String resizeImage(MultipartFile image, Integer maxLength) {
        BufferedImage originalImage = convertToBufferedImage(image);
        BufferedImage resizedImage = resizeImageToRatio(originalImage, maxLength);
        String savedImageName = imageService.save(image.getOriginalFilename(), resizedImage);
        return savedImageName;
    }

    private BufferedImage convertToBufferedImage(MultipartFile image) {
        try {
            return ImageIO.read(image.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("BufferedImage Conversion Failure");
        }
    }

    private BufferedImage resizeImageToRatio(BufferedImage originalImage, Integer maxLength) {
        if (originalImage.getWidth() < originalImage.getHeight()) {
            return Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, maxLength);
        }
        return Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_WIDTH, maxLength);
    }
}
