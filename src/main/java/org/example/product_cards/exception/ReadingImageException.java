package org.example.product_cards.exception;

public class ReadingImageException extends RuntimeException{
  private static final String DEFAULT_MESSAGE = "Error reading image from resources";
  public ReadingImageException() {
    super(DEFAULT_MESSAGE);
  }

  public ReadingImageException(String message) {
    super(message);
  }
}
