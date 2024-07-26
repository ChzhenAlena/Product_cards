package org.example.product_cards.service;

import lombok.RequiredArgsConstructor;
import org.example.product_cards.entity.ProductEntity;
import org.example.product_cards.exception.ProductNotFoundException;
import org.example.product_cards.model.ProductRequest;
import org.example.product_cards.model.ProductResponse;
import org.example.product_cards.repository.ProductRepository;
import org.example.product_cards.util.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final FileService fileService;
  private final ProductRepository productRepository;
  private final ProductMapper mapper;

  @Transactional(readOnly = true)
  public ProductResponse getById(UUID id) {
    ProductEntity productEntity = productRepository.findById(id)
        .orElseThrow(ProductNotFoundException::new);
    return mapper.toProductResponse(productEntity);
  }

  @Transactional(readOnly = true)
  public List<ProductResponse> getAll() {
    List<ProductEntity> productEntities = productRepository.findAll();
    return mapper.toResponseList(productEntities);
  }

  @Transactional
  public UUID create(ProductRequest request) {
    List<String> fileNames = fileService.processImages(request.image_urls(), request.name());

    ProductEntity productEntity = mapper.createRequestToEntity(request, fileNames);
    productEntity = productRepository.save(productEntity);
    return productEntity.getId();
  }

  @Transactional
  public void update(UUID id, ProductRequest request) {
    ProductEntity productEntity = productRepository.findById(id)
        .orElseThrow(ProductNotFoundException::new);

    List<String> oldFileNames = productEntity.getPhotos();
    List<String> fileNames = fileService.updateImages(request.image_urls(), request.name(), oldFileNames);
    mapper.updateEntity(productEntity, request, fileNames);
    productRepository.save(productEntity);
  }

  @Transactional
  public void delete(UUID id) {
    ProductEntity productEntity = productRepository.findById(id)
        .orElseThrow(ProductNotFoundException::new);
    fileService.deleteImagesFromResources(productEntity.getPhotos());
    productRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public List<ProductResponse> getWithHighestRating() {
    List<ProductEntity> productEntities = productRepository.findWithHighestRating();
    return mapper.toResponseList(productEntities);
  }

  @Transactional(readOnly = true)
  public List<ProductResponse> getWithHighestPrice() {
    List<ProductEntity> productEntities = productRepository.findWithHighestPrice();
    return mapper.toResponseList(productEntities);
  }

  @Transactional(readOnly = true)
  public List<ProductResponse> getWithLowestPrice() {
    List<ProductEntity> productEntities = productRepository.findWithLowestPrice();
    return mapper.toResponseList(productEntities);
  }

  @Transactional
  public void addImagesToTable(UUID productId, List<String> imageNames) {
    ProductEntity productEntity = productRepository.findById(productId)
        .orElseThrow(ProductNotFoundException::new);
    productEntity.getPhotos().addAll(imageNames);
  }

  @Transactional(readOnly = true)
  public ProductEntity getProductEntityById(UUID id) {
    return productRepository.findById(id)
        .orElseThrow(ProductNotFoundException::new);
  }
}

