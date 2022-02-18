package com.example.resizeimage.image.repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Repository
@Slf4j
public class ImageRepository {

    @Value("${image.path}")
    private String path;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public void save(String fileName, String extension, BufferedImage bufferedImage) {
        File fileToSave = new File(path + fileName);
        try {
            ImageIO.write(bufferedImage, extension, fileToSave);
            LocalDateTime now = LocalDateTime.now();
            log.info(fileToSave.getName() + " saved at " + dateTimeFormatter.format(now));
        } catch (IOException e) {
            throw new IllegalStateException("File Save Failed");
        }
    }

    public byte[] getFile(String fileName) {
        try {
            File file = new File(path + fileName);
            return IOUtils.toByteArray(new FileInputStream(file));
        } catch (IOException e) {
            throw new IllegalArgumentException("No Such File");
        }
    }

    public void deleteFiles(int days) {
        File imageDirectory = new File(path);
        if (!imageDirectory.exists()) {
            throw new IllegalStateException("Image Directory Not Found");
        }

        for (File file : imageDirectory.listFiles()) {
            long fileAge = new Date().getTime() - file.lastModified();
            long desiredAge = TimeUnit.DAYS.toMillis(days);
            if (fileAge > desiredAge) {
                LocalDateTime now = LocalDateTime.now();
                log.info(file.getName() + " deleted at " + dateTimeFormatter.format(now));
                file.delete();
            }
        }
    }
}
