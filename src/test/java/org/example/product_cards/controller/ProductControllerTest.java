package org.example.product_cards.controller;

import org.example.product_cards.model.ProductRequest;
import org.example.product_cards.model.ProductResponse;
import org.example.product_cards.service.ExternalService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

  @Mock
  private ExternalService service;
  @InjectMocks
  private ProductController productController;

  @Test
  void givenId_whenGetById_thenReturnProductResponse() {
    UUID id = UUID.randomUUID();
    ProductResponse response = Instancio.create(ProductResponse.class);

    when(service.getById(id)).thenReturn(response);

    ProductResponse expectedResponse = productController.getById(id);

    verify(service, times(1)).getById(id);
    assertEquals(expectedResponse, response);

  }

  @Test
  void whenGetAll_thenReturnProductResponses() {
    List<ProductResponse> responseList = List.of(
        Instancio.create(ProductResponse.class),
        Instancio.create(ProductResponse.class),
        Instancio.create(ProductResponse.class)
    );
    boolean withHighestRating = false;
    boolean withHighestPrice = false;
    boolean withLowestPrice = false;

    when(service.getAll(withHighestRating, withHighestPrice, withLowestPrice)).thenReturn(responseList);

    List<ProductResponse> expectedResponseList = productController.getAll(withHighestRating, withHighestPrice, withLowestPrice);

    verify(service, times(1)).getAll(withHighestRating, withHighestPrice, withLowestPrice);
    assertEquals(expectedResponseList, responseList);
  }

  @Test
  void givenProductRequest_whenCreate_thenReturnProductResponse() {
    ProductRequest request = Instancio.create(ProductRequest.class);
    ProductResponse response = Instancio.create(ProductResponse.class);

    when(service.create(request)).thenReturn(response);

    ProductResponse expectedResponse = productController.create(request);

    verify(service, times(1)).create(request);
    assertEquals(expectedResponse, response);
  }

  @Test
  void givenIdAndProductRequest_whenUpdate_thenReturnProductResponse() {
    UUID id = UUID.randomUUID();
    ProductRequest request = Instancio.create(ProductRequest.class);
    ProductResponse response = Instancio.create(ProductResponse.class);

    when(service.update(id, request)).thenReturn(response);

    ProductResponse expectedResponse = productController.update(id, request);

    verify(service, times(1)).update(id, request);
    assertEquals(expectedResponse, response);
  }

  @Test
  void givenId_whenDelete_thenPerformExpectedOperations() {
    UUID id = UUID.randomUUID();

    productController.delete(id);

    verify(service, times(1)).delete(id);
  }
}