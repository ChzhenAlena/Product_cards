package org.example.product_cards.repository;

import org.example.product_cards.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

  Optional<ProductEntity> findById(UUID id);

  @Query("""
    SELECT p from ProductEntity p
    WHERE p.rating in
      (SELECT p.rating from ProductEntity p
      order by p.rating desc limit 1
      )
  """)
  List<ProductEntity> findWithHighestRating();

  @Query("""
    SELECT p from ProductEntity p
    WHERE p.price in
      (SELECT p.price from ProductEntity p
      order by p.price desc limit 1
      )
  """)
  List<ProductEntity> findWithHighestPrice();

  @Query("""
    SELECT p from ProductEntity p
    WHERE p.price in
      (SELECT p.price from ProductEntity p
      order by p.price asc limit 1
      )
  """)
  List<ProductEntity> findWithLowestPrice();

  @Query("SELECT p.photos from ProductEntity p where p.id = :id")
  List<String> getImagesByProductId(UUID id);

}
