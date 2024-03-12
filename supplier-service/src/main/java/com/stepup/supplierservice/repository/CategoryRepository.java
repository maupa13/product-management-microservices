package com.stepup.supplierservice.repository;

import com.stepup.supplierservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository interface for managing categories.
 * This interface extends {@link org.springframework.data.jpa.repository.JpaRepository}, providing CRUD operations
 * (Create, Read, Update, Delete) and other common database operations for the {@link Category} entity.
 *
 * @see org.springframework.stereotype.Repository
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see Category
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
