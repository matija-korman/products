package com.example.products.controller.mapper;

import com.example.products.domain.Product;
import com.example.products.model.CreateProductRequest;
import com.example.products.model.ProductResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product productRequestToProduct(CreateProductRequest request);

    ProductResponse productToProductResponse(Product products);

    List<ProductResponse> productsToProductResponses(List<Product> products);
}
