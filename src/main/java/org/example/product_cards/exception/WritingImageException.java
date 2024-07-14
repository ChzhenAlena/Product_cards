package org.example.product_cards.exception;

public class WritingImageException extends RuntimeException{
  private static final String DEFAULT_MESSAGE = "Error writing image from resources";
  public WritingImageException() {
    super(DEFAULT_MESSAGE);
  }

  public WritingImageException(String message) {
    super(message);
  }
}
