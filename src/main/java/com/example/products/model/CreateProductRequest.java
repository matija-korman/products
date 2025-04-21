package com.example.products.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank @Size(min = 15, max = 15, message = "Code must be exactly 15 characters") String code,
        @NotBlank String name,
        @NotNull BigDecimal priceEur,
        @NotBlank String description
) {

}
