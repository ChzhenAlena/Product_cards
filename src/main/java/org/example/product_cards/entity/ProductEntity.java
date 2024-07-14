package org.example.product_cards.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "product")
public class ProductEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "brand", nullable = false)
  private String brand;

  @Column(name = "model")
  private String model;

  @Column(name = "available_units", nullable = false)
  private int availableUnits;

  @Column(name = "weight", nullable = false)
  private double weight;

  @Column(name = "rating", nullable = false)
  private double rating;

  @Column(name = "category", nullable = false)
  private String category;

  @Column(name = "description", nullable = false, length = 2000)
  private String description;

  @Column(name = "color", nullable = false)
  private String color;

  @Column(name = "price", nullable = false)
  private double price;

  @Column(name = "warranty", nullable = false)
  private String warranty;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(
      name = "product_feature",
      joinColumns = @JoinColumn(name = "product_id")
  )
  @Column(name = "feature")
  private List<String> features;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(
      name = "product_photo",
      joinColumns = @JoinColumn(name = "product_id")
  )
  @Column(name = "photo_name")
  private List<String> photos;

}