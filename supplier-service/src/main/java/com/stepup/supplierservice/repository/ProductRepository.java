package com.stepup.supplierservice.repository;

import com.stepup.supplierservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByPriceBetween(double min, double max);

    List<Product> findByPriceGreaterThan(double min);

    List<Product> findByPriceIsLessThan(double max);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByNameContainingIgnoreCase(String keyword);

    List<Product> findByNameNotContainingIgnoreCase(String name);

    @Query("SELECT p FROM Product p WHERE LOWER(p.description) LIKE CONCAT('%', LOWER(:keyword), '%')")
    List<Product> searchByDescriptionContaining(@Param("keyword") String keyword);

}
