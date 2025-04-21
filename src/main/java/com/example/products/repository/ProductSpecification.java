package com.example.products.repository;

import com.example.products.domain.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> filterByCodeAndName(String code, String name) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate p = cb.conjunction();

            if (code != null && !code.isBlank()) {
                p = cb.and(p, cb.like(cb.lower(root.get("code")), "%" + code.toLowerCase() + "%"));
            }

            if (name != null && !name.isBlank()) {
                p = cb.and(p, cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            return p;
        };
    }
}
