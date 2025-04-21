package com.example.products.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 15, max = 15, message = "Code must be exactly 15 characters")
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "price_eur")
    private BigDecimal priceEur;

    @Column(name = "price_usd")
    private BigDecimal priceUsd;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}
