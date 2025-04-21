package com.example.products.repository;

import com.example.products.domain.Product;
import com.example.products.model.PopularProduct;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("""
            SELECT new com.example.products.model.PopularProduct(
                p.name, ROUND(AVG(r.rating), 1)
            )
            FROM Product p
            JOIN Review r ON r.product.id = p.id
            GROUP BY p.id, p.code, p.name
            ORDER BY AVG(r.rating) DESC
            """)
    List<PopularProduct> findTopRatedProducts(Pageable pageable);

}
