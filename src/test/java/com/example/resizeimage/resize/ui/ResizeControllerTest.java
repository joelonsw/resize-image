package com.example.resizeimage.resize.ui;

import com.example.resizeimage.resize.service.ResizeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.FileInputStream;

import static com.example.resizeimage.resize.ResizeFixture.TEST_IMAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ResizeControllerTest {

    public static final String RESIZED_IMAGE_NAME = "resizedImage.jpg";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResizeService resizeService;

    @BeforeEach
    void setUp() {
        when(resizeService.resizeImage(any(), any())).thenReturn(RESIZED_IMAGE_NAME);
    }

    @Test
    @DisplayName("POST: /resize에 대해서 MultipartFile과 maxLength를 받을 수 있다")
    void resizeImage() throws Exception {
        MockMultipartFile mockTestImage = new MockMultipartFile("sea.jpg", new FileInputStream(TEST_IMAGE));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.multipart("/resize")
                .file("image", mockTestImage.getBytes())
                .param("maxLength", "800");

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse()
                .getContentAsString();
        assertThat(responseBody).isEqualTo(RESIZED_IMAGE_NAME);
    }
}
