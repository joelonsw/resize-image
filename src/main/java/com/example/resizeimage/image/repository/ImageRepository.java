package com.example.resizeimage.image.repository;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Repository
public class ImageRepository {

    @Value("${image.path}")
    private String path;

    public void save(String fileName, String extension, BufferedImage bufferedImage) {
        File fileToSave = new File(path + fileName);
        try {
            ImageIO.write(bufferedImage, extension, fileToSave);
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
}
