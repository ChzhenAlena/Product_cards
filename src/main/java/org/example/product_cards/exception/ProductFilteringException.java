package org.example.product_cards.exception;

public class ProductFilteringException extends RuntimeException{
  private static final String DEFAULT_MESSAGE = "You can not choose more than 1 filter terms";
  public ProductFilteringException() {
    super(DEFAULT_MESSAGE);
  }

  public ProductFilteringException(String message) {
    super(message);
  }
}
