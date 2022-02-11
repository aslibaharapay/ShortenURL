package com.asli.shortener.url.api.controller;

import com.asli.shortener.url.api.dto.UrlRequest;
import com.asli.shortener.url.api.service.UrlService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = ShortenUrlController.class)
public class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UrlService urlService;

    private JacksonTester<UrlRequest> shorteningRequest;

    @Before
    public void setup() {
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void should_returnShortedUrl_whenUrlIsValid_CreateShortUrl() throws Exception {

        UrlRequest dto = new UrlRequest();
        dto.setUrl("https://www.upwork.com/ab/payments/reports/certificate-of-earnings.pdf");
        String json = shorteningRequest.write(dto).getJson();
        given(urlService.createShortUrl(any(UrlRequest.class))).willReturn("b");


        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                        "/shortenApi/v1/create/shorturl").content(json)
                .contentType(MediaType.APPLICATION_JSON);


        mockMvc.perform(requestBuilder).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("b"))
                .andReturn();
    }


    @Test
    public void should_redirectUrl_whenUrlIsReturned_GetLongUrl() throws Exception {

        //Given
        final String KEY = "f";
        given(urlService.getLongUrl(any(String.class))).willReturn(KEY);

        //When
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/shortenApi/v1/" + KEY);

        mockMvc.perform(requestBuilder).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(KEY))
                .andReturn();
    }

}
