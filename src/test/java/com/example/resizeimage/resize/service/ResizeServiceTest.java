package com.example.resizeimage.resize.service;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;

import static com.example.resizeimage.resize.ResizeFixture.TEST_IMAGE;
import static org.assertj.core.api.Assertions.assertThat;

class ResizeServiceTest {

    private final ResizeService resizeService = new ResizeService();

    @Test
    @DisplayName("이미지의 긴 쪽을 800px로 조절하며, 파일의 이름에는 오늘의 날짜가 포함된다")
    void resizeTo800() throws IOException {
        // given
        MultipartFile multipartFile = convertToMultipartFile(TEST_IMAGE);

        // when
        String resizedImageName = resizeService.resizeImage(multipartFile, 800);

        // then
        assertThat(resizedImageName).contains(LocalDate.now().toString());
    }

    private MultipartFile convertToMultipartFile(File file) throws IOException {
        FileInputStream input = new FileInputStream(file);
        return new MockMultipartFile("file", file.getName(), "image/jpeg", IOUtils.toByteArray(input));
    }
}
