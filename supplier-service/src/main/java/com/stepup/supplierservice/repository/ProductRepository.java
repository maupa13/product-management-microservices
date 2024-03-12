package com.stepup.supplierservice.repository;

import com.stepup.supplierservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository interface for managing products.
 *
 * @see org.springframework.stereotype.Repository
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see Product
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Retrieves products with prices within the specified range.
     *
     * @param min the minimum price
     * @param max the maximum price
     * @return a list of products with prices within the specified range
     */
    List<Product> findByPriceBetween(double min, double max);

    /**
     * Retrieves products with prices greater than the specified value.
     *
     * @param min the minimum price
     * @return a list of products with prices greater than the specified value
     */
    List<Product> findByPriceGreaterThan(double min);

    /**
     * Retrieves products with prices less than the specified value.
     *
     * @param max the maximum price
     * @return a list of products with prices less than the specified value
     */
    List<Product> findByPriceIsLessThan(double max);

    /**
     * Retrieves products belonging to the specified category.
     *
     * @param categoryId the ID of the category
     * @return a list of products belonging to the specified category
     */
    List<Product> findByCategoryId(Long categoryId);

    /**
     * Retrieves products with names containing the specified keyword (case-insensitive).
     *
     * @param keyword the keyword to search for in product names
     * @return a list of products with names containing the specified keyword
     */
    List<Product> findByNameContainingIgnoreCase(String keyword);

    /**
     * Retrieves products with names not containing the specified keyword (case-insensitive).
     *
     * @param name the keyword to exclude from product names
     * @return a list of products with names not containing the specified keyword
     */
    List<Product> findByNameNotContainingIgnoreCase(String name);

    /**
     * Retrieves products with descriptions containing the specified keyword (case-insensitive).
     * This method uses a custom JPQL query to perform the search.
     *
     * @param keyword the keyword to search for in product descriptions
     * @return a list of products with descriptions containing the specified keyword
     */
    @Query("SELECT p FROM Product p WHERE LOWER(p.description) LIKE CONCAT('%', LOWER(:keyword), '%')")
    List<Product> searchByDescriptionContaining(@Param("keyword") String keyword);

}
