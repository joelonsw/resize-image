package com.example.resizeimage.resize.service;

import com.example.resizeimage.image.repository.ImageRepository;
import com.example.resizeimage.image.service.ImageService;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.example.resizeimage.resize.ResizeFixture.TEST_IMAGE;
import static org.assertj.core.api.Assertions.assertThat;

class ResizeServiceTest {

    private final ResizeService resizeService = new ResizeService(new ImageService(new ImageRepository()));

    @Test
    @DisplayName("이미지의 긴 쪽을 800px로 조절하며, 파일의 이름에는 오늘의 날짜가 포함된다")
    void resizeTo800() throws IOException {
        // given
        MultipartFile multipartFile = convertToMultipartFile(TEST_IMAGE);

        // when
        String resizedImageName = resizeService.resizeImage(multipartFile, 800, "automatic");

        // then
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        assertThat(resizedImageName).contains(LocalDate.now().format(formatter));
    }

    private MultipartFile convertToMultipartFile(File file) throws IOException {
        FileInputStream input = new FileInputStream(file);
        return new MockMultipartFile("file", file.getName(), "image/jpeg", IOUtils.toByteArray(input));
    }
}
