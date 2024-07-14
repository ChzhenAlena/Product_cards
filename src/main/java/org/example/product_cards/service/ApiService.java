package org.example.product_cards.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URL;


@Service
@RequiredArgsConstructor
public class ApiService {

  private final RestTemplate restTemplate;
  private static final String RESOURCE_URL = "https://api.remove.bg/v1.0/removebg";
  private static final String ACCESS_HEADER_KEY = "X-API-Key";

  @Value("${headers.X-API-Key}")
  private String accessHeaderValue = "X-API-Key";

  public byte[] sendRequestToRemoveBackground(URL imageUrl) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.add(ACCESS_HEADER_KEY, accessHeaderValue);

    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("image_url", imageUrl.toString());

    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

    ResponseEntity<byte[]> response = restTemplate.postForEntity(RESOURCE_URL, requestEntity, byte[].class);

    return response.getBody();
  }

}
