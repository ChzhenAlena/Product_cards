package org.example.product_cards.controller;

import lombok.RequiredArgsConstructor;
import org.example.product_cards.exception.ErrorResponse;
import org.example.product_cards.exception.ProductFilteringException;
import org.example.product_cards.exception.ProductNotFoundException;
import org.example.product_cards.model.ProductRequest;
import org.example.product_cards.model.ProductResponse;
import org.example.product_cards.service.ExternalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

  private final ExternalService service;

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ProductResponse getById(@PathVariable UUID id) {
    return service.getById(id);
  }

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  public List<ProductResponse> getAll(
      @RequestParam(required = false) boolean withHighestRating,
      @RequestParam(required = false) boolean withHighestPrice,
      @RequestParam(required = false) boolean withLowestPrice
  ) {
    return service.getAll(withHighestRating, withHighestPrice, withLowestPrice);
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public ProductResponse create(@RequestBody ProductRequest request){
    return service.create(request);
  }

  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ProductResponse update(@PathVariable UUID id, @RequestBody ProductRequest request){
    return service.update(id, request);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id){
    service.delete(id);
  }

  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ProductFilteringException.class)
  public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductFilteringException ex) {
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

}
