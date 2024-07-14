package org.example.product_cards.service;

import org.example.product_cards.entity.ProductEntity;
import org.example.product_cards.exception.ProductNotFoundException;
import org.example.product_cards.model.ProductRequest;
import org.example.product_cards.model.ProductResponse;
import org.example.product_cards.repository.ProductRepository;
import org.example.product_cards.util.ProductMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  @Mock
  private ProductRepository productRepository;
  @Mock
  private FileService fileService;
  @Spy
  private ProductMapper productMapper;
  @InjectMocks
  private ProductService productService;

  @Test
  void givenExistingId_whenGetById_thenReturnProductResponse() {
    UUID id = UUID.randomUUID();
    ProductEntity productEntity = Instancio.create(ProductEntity.class);
    ProductResponse response = Instancio.create(ProductResponse.class);
    Optional<ProductEntity> optionalProductEntity = Optional.of(productEntity);

    when(productRepository.findById(id)).thenReturn(optionalProductEntity);
    when(productMapper.toProductResponse(productEntity)).thenReturn(response);

    ProductResponse expectedResult = productService.getById(id);

    verify(productRepository, times(1)).findById(id);
    verify(productMapper, times(1)).toProductResponse(productEntity);
    assertEquals(expectedResult, response);
  }

  @Test
  void givenNotExistingId_whenGetById_thenThrowException() {
    UUID id = UUID.randomUUID();
    Optional<ProductEntity> optionalProductEntity = Optional.empty();

    when(productRepository.findById(id)).thenReturn(optionalProductEntity);

    assertThrows(ProductNotFoundException.class, () -> productService.getById(id));
  }

  @Test
  void whenGetAll_thenReturnProductResponses() {
    List<ProductEntity> productEntities = List.of(
        Instancio.create(ProductEntity.class),
        Instancio.create(ProductEntity.class),
        Instancio.create(ProductEntity.class)
    );
    List<ProductResponse> response = List.of(
        Instancio.create(ProductResponse.class),
        Instancio.create(ProductResponse.class),
        Instancio.create(ProductResponse.class)
    );

    when(productRepository.findAll()).thenReturn(productEntities);
    when(productMapper.toResponseList(productEntities)).thenReturn(response);

    List<ProductResponse> expectedResult = productService.getAll();

    verify(productRepository, times(1)).findAll();
    verify(productMapper, times(1)).toResponseList(productEntities);
    assertEquals(expectedResult, response);
  }

  @Test
  void givenProductRequest_whenCreate_thenReturnId() {
    List<String> fileNames = List.of(
        Instancio.create(String.class),
        Instancio.create(String.class)
    );
    ProductRequest productRequest = Instancio.create(ProductRequest.class);
    ProductEntity productEntity = Instancio.create(ProductEntity.class);

    when(fileService.processImages(productRequest.image_urls(), productRequest.name())).thenReturn(fileNames);
    when(productMapper.createRequestToEntity(productRequest, fileNames)).thenReturn(productEntity);
    when(productRepository.save(productEntity)).thenReturn(productEntity);

    UUID expectedId = productService.create(productRequest);

    verify(fileService, times(1)).processImages(productRequest.image_urls(), productRequest.name());
    verify(productMapper, times(1)).createRequestToEntity(productRequest, fileNames);
    verify(productRepository, times(1)).save(productEntity);
    assertEquals(expectedId, productEntity.getId());

  }

  @Test
  void givenExistingIdAndProductRequest_whenUpdate_thenPerformExpectedOperations() {
    List<String> oldFileNames = List.of(
        Instancio.create(String.class),
        Instancio.create(String.class)
    );
    List<String> fileNames = List.of(
        Instancio.create(String.class),
        Instancio.create(String.class)
    );
    UUID id = UUID.randomUUID();
    ProductRequest productRequest = Instancio.create(ProductRequest.class);
    ProductEntity productEntity = Instancio.create(ProductEntity.class);
    Optional<ProductEntity> optionalProductEntity = Optional.of(productEntity);

    when(productRepository.findById(id)).thenReturn(optionalProductEntity);
    when(fileService.updateImages(productRequest.image_urls(), productRequest.name(),productEntity.getPhotos())).thenReturn(fileNames);
    when(productMapper.updateEntity(productEntity, productRequest, fileNames)).thenReturn(productEntity);
    when(productRepository.save(productEntity)).thenReturn(productEntity);

    productService.update(id, productRequest);

    verify(productRepository, times(1)).findById(id);
    verify(fileService, times(1)).updateImages(productRequest.image_urls(), productRequest.name(),productEntity.getPhotos());
    verify(productMapper, times(1)).updateEntity(productEntity, productRequest, fileNames);
    verify(productRepository, times(1)).save(productEntity);
  }

  @Test
  void givenNotExistingIdAndProductRequest_whenUpdate_thenPerformExpectedOperations() {
    UUID id = UUID.randomUUID();
    Optional<ProductEntity> optionalProductEntity = Optional.empty();

    when(productRepository.findById(id)).thenReturn(optionalProductEntity);

    assertThrows(ProductNotFoundException.class, () -> productService.getById(id));
  }

  @Test
  void givenExistingId_whenDelete_thenPerformExpectedOperations() {
    UUID id = UUID.randomUUID();
    ProductEntity productEntity = Instancio.create(ProductEntity.class);
    Optional<ProductEntity> optionalProductEntity = Optional.of(productEntity);

    when(productRepository.findById(id)).thenReturn(optionalProductEntity);

    productService.delete(id);

    verify(productRepository, times(1)).findById(id);
    verify(fileService, times(1)).deleteImagesFromResources(productEntity.getPhotos());
    verify(productRepository, times(1)).deleteById(id);
  }

  @Test
  void givenNotExistingId_whenDelete_thenThrowException() {
    UUID id = UUID.randomUUID();
    Optional<ProductEntity> optionalProductEntity = Optional.empty();

    when(productRepository.findById(id)).thenReturn(optionalProductEntity);

    assertThrows(ProductNotFoundException.class, () -> productService.getById(id));
  }

  @Test
  void whenGetWithHighestRating_thenReturnProductResponseList() {
    List<ProductEntity> productEntities = List.of(
        Instancio.create(ProductEntity.class),
        Instancio.create(ProductEntity.class),
        Instancio.create(ProductEntity.class)
    );
    List<ProductResponse> response = List.of(
        Instancio.create(ProductResponse.class),
        Instancio.create(ProductResponse.class),
        Instancio.create(ProductResponse.class)
    );

    when(productRepository.findWithHighestRating()).thenReturn(productEntities);
    when(productMapper.toResponseList(productEntities)).thenReturn(response);

    List<ProductResponse> expectedResult = productService.getWithHighestRating();

    verify(productRepository, times(1)).findWithHighestRating();
    verify(productMapper, times(1)).toResponseList(productEntities);
    assertEquals(expectedResult, response);
  }

  @Test
  void whenGetWithHighestPrice_thenReturnProductResponseList() {
    List<ProductEntity> productEntities = List.of(
        Instancio.create(ProductEntity.class),
        Instancio.create(ProductEntity.class),
        Instancio.create(ProductEntity.class)
    );
    List<ProductResponse> response = List.of(
        Instancio.create(ProductResponse.class),
        Instancio.create(ProductResponse.class),
        Instancio.create(ProductResponse.class)
    );

    when(productRepository.findWithHighestPrice()).thenReturn(productEntities);
    when(productMapper.toResponseList(productEntities)).thenReturn(response);

    List<ProductResponse> expectedResult = productService.getWithHighestPrice();

    verify(productRepository, times(1)).findWithHighestPrice();
    verify(productMapper, times(1)).toResponseList(productEntities);
    assertEquals(expectedResult, response);
  }

  @Test
  void whenGetWithLowestPrice_thenReturnProductResponseList() {
    List<ProductEntity> productEntities = List.of(
        Instancio.create(ProductEntity.class),
        Instancio.create(ProductEntity.class),
        Instancio.create(ProductEntity.class)
    );
    List<ProductResponse> response = List.of(
        Instancio.create(ProductResponse.class),
        Instancio.create(ProductResponse.class),
        Instancio.create(ProductResponse.class)
    );

    when(productRepository.findWithLowestPrice()).thenReturn(productEntities);
    when(productMapper.toResponseList(productEntities)).thenReturn(response);

    List<ProductResponse> expectedResult = productService.getWithLowestPrice();

    verify(productRepository, times(1)).findWithLowestPrice();
    verify(productMapper, times(1)).toResponseList(productEntities);
    assertEquals(expectedResult, response);
  }

  @Test
  void givenExistingProductIdAndImageNames_whenAddImagesToTable_thenPerformExpectedOperations() {
    UUID id = UUID.randomUUID();
    ProductEntity productEntity = Instancio.create(ProductEntity.class);
    Optional<ProductEntity> optionalProductEntity = Optional.of(productEntity);
    List<String> imageNames = List.of("test.png");

    when(productRepository.findById(id)).thenReturn(optionalProductEntity);

    productService.addImagesToTable(id, imageNames);

    verify(productRepository, times(1)).findById(id);
    assertTrue(productEntity.getPhotos().containsAll(imageNames));
  }

  @Test
  void givenNotExistingProductIdAndImageNames_whenAddImagesToTable_thenThrowException() {
    UUID id = UUID.randomUUID();
    Optional<ProductEntity> optionalProductEntity = Optional.empty();

    when(productRepository.findById(id)).thenReturn(optionalProductEntity);

    assertThrows(ProductNotFoundException.class, () -> productService.getById(id));
  }

}