package org.example.product_cards.controller;

import org.example.product_cards.model.AddImagesRequest;
import org.example.product_cards.service.ExternalService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileControllerTest {

  @Mock
  private ExternalService service;
  @InjectMocks
  private FileController fileController;

  @Test
  void givenFileName_whenGetImageByName_thenReturnImage() {
    String name = Instancio.create(String.class);
    byte[] image = Instancio.create(byte[].class);

    when(service.getImageByName(name)).thenReturn(image);

    byte[] expectedImage = fileController.getImageByName(name);

    verify(service, times(1)).getImageByName(name);
    assertEquals(expectedImage, image);

  }

  @Test
  void givenAddImagesRequest_whenAddImages_thenPerformExpectedOperations() {
    AddImagesRequest request = Instancio.create(AddImagesRequest.class);

    fileController.addImages(request);

    verify(service, times(1)).addImages(request);
  }
}