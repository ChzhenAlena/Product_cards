package org.example.product_cards.service;

import lombok.RequiredArgsConstructor;
import org.example.product_cards.exception.ProductFilteringException;
import org.example.product_cards.model.AddImagesRequest;
import org.example.product_cards.model.ProductRequest;
import org.example.product_cards.model.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExternalService {
  private final FileService fileService;
  private final ProductService productService;

  public ProductResponse getById(UUID id) {
    return productService.getById(id);
  }

  public List<ProductResponse> getAll(boolean withHighestRating,
                                      boolean withHighestPrice, boolean withLowestPrice) {
    if (
        (withHighestRating && withHighestPrice) ||
            (withHighestRating && withLowestPrice) ||
            (withHighestPrice && withLowestPrice)) {
      throw new ProductFilteringException();
    }
    if (withHighestRating) {
      return productService.getWithHighestRating();
    }
    if (withHighestPrice) {
      return productService.getWithHighestPrice();
    }
    if (withLowestPrice) {
      return productService.getWithLowestPrice();
    }
    return productService.getAll();
  }

  public ProductResponse create(ProductRequest request) {
    UUID id = productService.create(request);
    return productService.getById(id);
  }

  public ProductResponse update(UUID id, ProductRequest request) {
    productService.update(id, request);
    return productService.getById(id);
  }

  public void delete(UUID id) {
    productService.delete(id);
  }

  public byte[] getImageByName(String name) {
    return fileService.getImage(name);
  }

  public void addImages(AddImagesRequest request) {
    ProductResponse product = productService.getById(request.productId());
    List<String> imageNames = fileService.processImages(request.imageUrls(), product.name());
    productService.addImagesToTable(product.id(), imageNames);
  }
}
