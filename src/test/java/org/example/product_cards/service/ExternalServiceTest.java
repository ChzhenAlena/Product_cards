package org.example.product_cards.service;

import org.example.product_cards.exception.ProductFilteringException;
import org.example.product_cards.model.AddImagesRequest;
import org.example.product_cards.model.ProductRequest;
import org.example.product_cards.model.ProductResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExternalServiceTest {

  @Mock
  private ProductService productService;
  @Mock
  private FileService fileService;
  @InjectMocks
  private ExternalService externalService;

  @Test
  void givenId_whenGetById_thenReturnProductResponse() {
    UUID id = UUID.randomUUID();
    ProductResponse response = Instancio.create(ProductResponse.class);

    when(productService.getById(id)).thenReturn(response);

    ProductResponse expectedResponse = externalService.getById(id);

    verify(productService, times(1)).getById(id);
    assertEquals(expectedResponse, response);
  }

  @Test
  void givenNoParams_whenGetAll_thenReturnProductResponses() {
    List<ProductResponse> responseList = List.of(
        Instancio.create(ProductResponse.class),
        Instancio.create(ProductResponse.class),
        Instancio.create(ProductResponse.class)
    );
    boolean withHighestRating = false;
    boolean withHighestPrice = false;
    boolean withLowestPrice = false;

    when(productService.getAll()).thenReturn(responseList);

    List<ProductResponse> resultResponseList = externalService.getAll(withHighestRating, withHighestPrice, withLowestPrice);

    verify(productService, times(1)).getAll();
    assertEquals(resultResponseList, responseList);

  }

  @Test
  void givenHighestRatingParam_whenGetAll_thenReturnProductResponses() {
    List<ProductResponse> responseList = List.of(
        Instancio.create(ProductResponse.class),
        Instancio.create(ProductResponse.class),
        Instancio.create(ProductResponse.class)
    );
    boolean withHighestRating = true;
    boolean withHighestPrice = false;
    boolean withLowestPrice = false;

    when(productService.getWithHighestRating()).thenReturn(responseList);

    List<ProductResponse> resultResponseList = externalService.getAll(withHighestRating, withHighestPrice, withLowestPrice);

    verify(productService, times(1)).getWithHighestRating();
    assertEquals(resultResponseList, responseList);

  }

  @Test
  void givenHighestPriceParam_whenGetAll_thenReturnProductResponses() {
    List<ProductResponse> responseList = List.of(
        Instancio.create(ProductResponse.class),
        Instancio.create(ProductResponse.class),
        Instancio.create(ProductResponse.class)
    );
    boolean withHighestRating = false;
    boolean withHighestPrice = true;
    boolean withLowestPrice = false;

    when(productService.getWithHighestPrice()).thenReturn(responseList);

    List<ProductResponse> resultResponseList = externalService.getAll(withHighestRating, withHighestPrice, withLowestPrice);

    verify(productService, times(1)).getWithHighestPrice();
    assertEquals(resultResponseList, responseList);

  }

  @Test
  void givenLowestPriceParam_whenGetAll_thenReturnProductResponses() {
    List<ProductResponse> responseList = List.of(
        Instancio.create(ProductResponse.class),
        Instancio.create(ProductResponse.class),
        Instancio.create(ProductResponse.class)
    );
    boolean withHighestRating = false;
    boolean withHighestPrice = false;
    boolean withLowestPrice = true;

    when(productService.getWithLowestPrice()).thenReturn(responseList);

    List<ProductResponse> resultResponseList = externalService.getAll(withHighestRating, withHighestPrice, withLowestPrice);

    verify(productService, times(1)).getWithLowestPrice();
    assertEquals(resultResponseList, responseList);

  }

  @Test
  void givenTwoConditions_whenGetAll_thenThrowException() {
    boolean withHighestRating = true;
    boolean withHighestPrice = false;
    boolean withLowestPrice = true;

    assertThrows(ProductFilteringException.class,
        () -> externalService.getAll(withHighestRating, withHighestPrice, withLowestPrice));
  }

  @Test
  void givenProductRequest_whenCreate_thenReturnProductResponse() {
    UUID id = UUID.randomUUID();
    ProductRequest request = Instancio.create(ProductRequest.class);
    ProductResponse response = Instancio.create(ProductResponse.class);

    when(productService.create(request)).thenReturn(id);
    when(productService.getById(id)).thenReturn(response);

    ProductResponse expectedResponse = externalService.create(request);

    verify(productService, times(1)).create(request);
    verify(productService, times(1)).getById(id);
    assertEquals(expectedResponse, response);
  }

  @Test
  void givenProductRequestAndId_whenUpdate_thenReturnProductResponse() {
    UUID id = UUID.randomUUID();
    ProductRequest request = Instancio.create(ProductRequest.class);
    ProductResponse response = Instancio.create(ProductResponse.class);

    when(productService.getById(id)).thenReturn(response);

    ProductResponse expectedResponse = externalService.update(id, request);

    verify(productService, times(1)).update(id, request);
    verify(productService, times(1)).getById(id);
    assertEquals(expectedResponse, response);
  }

  @Test
  void givenId_whenDelete_thenPerformExpectedOperations() {
    UUID id = UUID.randomUUID();

    externalService.delete(id);

    verify(productService, times(1)).delete(id);
  }

  @Test
  void givenFileName_whenGetImageByName_thenReturnImage() {
    String name = Instancio.create(String.class);
    byte[] image = Instancio.create(byte[].class);

    when(fileService.getImage(name)).thenReturn(image);

    byte[] expectedImage = externalService.getImageByName(name);

    verify(fileService, times(1)).getImage(name);
    assertEquals(expectedImage, image);

  }

  @Test
  void givenAddImagesRequest_whenAddImages_thenPerformExpectedOperations() {
    AddImagesRequest request = Instancio.create(AddImagesRequest.class);
    ProductResponse product = Instancio.create(ProductResponse.class);
    List<String> imageNames = List.of(
        Instancio.create(String.class),
        Instancio.create(String.class)
    );

    when(productService.getById(request.productId())).thenReturn(product);
    when(fileService.processImages(request.imageUrls(), product.name())).thenReturn(imageNames);

    externalService.addImages(request);

    verify(productService, times(1)).getById(request.productId());
    verify(fileService, times(1)).processImages(request.imageUrls(), product.name());
    verify(productService,times(1)).addImagesToTable(product.id(), imageNames);

  }
}