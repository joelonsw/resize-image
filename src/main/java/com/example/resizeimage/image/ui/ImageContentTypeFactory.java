package com.example.resizeimage.image.ui;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

@RequiredArgsConstructor
public enum ImageContentTypeFactory {

    PNG("png", MediaType.IMAGE_PNG),
    GIF("gif", MediaType.IMAGE_GIF),
    JPEG("jpeg", MediaType.IMAGE_JPEG),
    JPG("jpg", MediaType.IMAGE_JPEG),
    SVG("svg", MediaType.parseMediaType("image/svg+xml"));

    private final String extension;
    private final MediaType mediaType;

    public static ResponseEntity<byte[]> generateResponseEntity(String fileName, byte[] file) {
        String extension = FilenameUtils.getExtension(fileName);

        ImageContentTypeFactory imageContentType = Arrays.stream(values())
                .filter(contentType -> contentType.extension.equalsIgnoreCase(extension))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Image Format Not Supported"));

        return ResponseEntity.status(HttpStatus.OK).contentType(imageContentType.mediaType).body(file);
    }

}
