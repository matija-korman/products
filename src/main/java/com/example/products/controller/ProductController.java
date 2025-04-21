package com.example.products.controller;

import com.example.products.controller.mapper.ProductMapper;
import com.example.products.model.CreateProductRequest;
import com.example.products.model.PagedResponse;
import com.example.products.model.PopularProductResponse;
import com.example.products.model.ProductResponse;
import com.example.products.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper mapper;


    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid CreateProductRequest request) {
        var product = mapper.productRequestToProduct(request);
        var productResponse = mapper.productToProductResponse(productService.createProduct(product));
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);

    }

    @GetMapping
    public PagedResponse<ProductResponse> getProducts(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            Pageable pageable
    ) {
        var page = productService.getFilteredProducts(code, name, pageable);
        var productResponses = mapper.productsToProductResponses(page.getContent());
        return new PagedResponse<>(
                productResponses,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );

    }

    @GetMapping("/popular")
    public List<PopularProductResponse> getPopularProducts() {
        return productService.getTopRatedProducts();
    }
}
