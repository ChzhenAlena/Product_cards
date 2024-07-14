package org.example.product_cards.model;

import java.util.List;
import java.util.UUID;

public record ProductResponse (
  UUID id,
  String name,
  String brand,
  String model,
  int availableUnits,
  double weight,
  double rating,
  String category,
  String description,
  String color,
  double price,
  String warranty,
  List<String> features,
  List<String> photo_names

) {

}
