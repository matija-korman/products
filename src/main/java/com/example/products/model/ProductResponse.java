package com.example.products.model;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String code,
        String name,
        BigDecimal priceEur,
        BigDecimal priceUsd,
        String description) {
}
