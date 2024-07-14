package org.example.product_cards.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.net.URL;
import java.util.List;

public record ProductRequest(
    @NotEmpty
    String name,
    @NotEmpty
    String description,
    @NotEmpty
    @Positive
    double price,
    @NotEmpty
    String color,
    @NotEmpty
    String brand,
    @Nullable
    String model,
    @NotEmpty
    String category,
    @NotEmpty
    @PositiveOrZero
    int available_units, // added instead of availability
    @NotEmpty
    double rating,
    List<URL> image_urls,
    @NotEmpty
    @Positive
    double weight,
    @NotEmpty
    String warranty,
    List<String> special_features

) {

}
