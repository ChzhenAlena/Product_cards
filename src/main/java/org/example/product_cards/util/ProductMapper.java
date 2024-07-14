package org.example.product_cards.util;

import org.example.product_cards.entity.ProductEntity;
import org.example.product_cards.model.ProductRequest;
import org.example.product_cards.model.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

  @Mapping(target = "photo_names", source = "photos")
  ProductResponse toProductResponse(ProductEntity productEntity);
  List<ProductResponse> toResponseList(List<ProductEntity> productEntity);

  @Mapping(target = "availableUnits", source = "request.available_units")
  @Mapping(target = "features", source = "request.special_features")
  @Mapping(target = "photos", source = "photoNames")
  ProductEntity createRequestToEntity(ProductRequest request, List<String> photoNames);

  @Mapping(target = "photos", source = "photoNames")
  ProductEntity updateEntity(@MappingTarget ProductEntity entity, ProductRequest request, List<String> photoNames);

}

