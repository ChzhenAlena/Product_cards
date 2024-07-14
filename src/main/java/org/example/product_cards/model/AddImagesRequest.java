package org.example.product_cards.model;

import jakarta.validation.constraints.NotEmpty;

import java.net.URL;
import java.util.List;
import java.util.UUID;

public record AddImagesRequest(
    @NotEmpty
    UUID productId,
    @NotEmpty
    List<URL> imageUrls

) {

}
