package com.example.resizeimage.image.service;

import com.example.resizeimage.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ImageService {

    private static final String DATE_TIME_FORMAT = "yyyyMMdd-HHmmss-";

    private final ImageRepository imageRepository;

    public String save(String imageName, BufferedImage bufferedImage) {
        String extension = FilenameUtils.getExtension(imageName);
        String currentTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
        String fileName = currentTime + imageName;
        imageRepository.save(fileName, extension, bufferedImage);
        return fileName;
    }

    public byte[] getFile(String fileName) {
        return imageRepository.getFile(fileName);
    }
}
