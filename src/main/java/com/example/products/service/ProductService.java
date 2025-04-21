package com.example.products.service;

import com.example.products.domain.Product;
import com.example.products.model.PopularProductResponse;
import com.example.products.repository.ProductRepository;
import com.example.products.repository.ProductSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@AllArgsConstructor
public class ProductService {

    public static final int NUM_OF_POPULAR_PRODUCTS = 3;

    private final ProductRepository productRepository;
    private final ExchangeRateService exchangeRateService;


    public Product createProduct(Product product) {
        setUSDPrice(product);
        return productRepository.save(product);
    }

    private void setUSDPrice(Product product) {
        BigDecimal priceEur = product.getPriceEur();
        BigDecimal usdExchangeRate = exchangeRateService.getUsdExchangeRate();
        BigDecimal priceUsd = priceEur.multiply(usdExchangeRate);

        product.setPriceUsd(priceUsd);
    }

    public Page<Product> getFilteredProducts(String code, String name, Pageable pageable) {
        return productRepository.findAll(ProductSpecification.filterByCodeAndName(code, name), pageable);
    }

    public List<PopularProductResponse> getTopRatedProducts() {
        return productRepository.findTopRatedProducts(PageRequest.of(0, NUM_OF_POPULAR_PRODUCTS));
    }
}
