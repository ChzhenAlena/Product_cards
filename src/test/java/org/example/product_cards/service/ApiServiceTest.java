package org.example.product_cards.service;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiServiceTest {

  @Mock
  private RestTemplate restTemplateMock;
  @InjectMocks
  private ApiService apiService;

  @Test
  void givenImageUrl_whenSendRequestToRemoveBackground_thenReturnImageBytes() throws Exception {
    byte[] mockResponseBody = Instancio.create(byte[].class);
    ResponseEntity<byte[]> mockResponse = new ResponseEntity<>(mockResponseBody, HttpStatus.OK);

    when(restTemplateMock.postForEntity(
        eq("https://api.remove.bg/v1.0/removebg"),
        any(HttpEntity.class),
        eq(byte[].class)
    )).thenReturn(mockResponse);

    URL imageUrl = new URL("https://example.com/image.jpg");
    MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
    requestBody.add("image_url", "https://example.com/image.jpg");
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-API-Key", "test-api-key");
    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
    byte[] responseBody = apiService.sendRequestToRemoveBackground(imageUrl);

    assertArrayEquals(mockResponseBody, responseBody);
  }

}