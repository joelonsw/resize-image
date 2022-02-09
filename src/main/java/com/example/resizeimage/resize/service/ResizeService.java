package com.example.resizeimage.resize.service;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class ResizeService {
    public String resizeImage(MultipartFile image, Integer maxLength) {
        BufferedImage originalImage = convertToBufferedImage(image);
        BufferedImage resizedImage = resizeImageToRatio(originalImage, maxLength);
        // TODO : imageService를 주입받아 저장하는 로직 구현
        return "image.png";
    }

    private BufferedImage convertToBufferedImage(MultipartFile image) {
        try {
            return ImageIO.read(image.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("BufferedImage Conversion Failure");
        }
    }

    private BufferedImage resizeImageToRatio(BufferedImage originalImage, Integer maxLength) {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        if (originalWidth < originalHeight) {
            return Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, maxLength);
        }
        return Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_WIDTH, maxLength);
    }
}
