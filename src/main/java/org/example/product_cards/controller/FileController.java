package org.example.product_cards.controller;

import lombok.RequiredArgsConstructor;
import org.example.product_cards.exception.ErrorResponse;
import org.example.product_cards.exception.ProductNotFoundException;
import org.example.product_cards.exception.ReadingImageException;
import org.example.product_cards.exception.WritingImageException;
import org.example.product_cards.model.AddImagesRequest;
import org.example.product_cards.service.ExternalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

  private final ExternalService service;

  @GetMapping(
      value = "/{name}",
      produces = MediaType.IMAGE_PNG_VALUE
  )
  public byte[] getImageByName(@PathVariable String name) {
    return service.getImageByName(name);
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.OK)
  public void addImages(@RequestBody AddImagesRequest request) {
    service.addImages(request);
  }

  @ExceptionHandler({ReadingImageException.class, WritingImageException.class})
  public ResponseEntity<ErrorResponse> handleImageProcessingExceptions(ProductNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
