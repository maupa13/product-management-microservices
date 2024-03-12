package com.stepup.supplierservice.service;

import com.stepup.supplierservice.dto.CategoryDto;
import com.stepup.supplierservice.entity.Category;
import com.stepup.supplierservice.exception.CategoryServiceException;
import com.stepup.supplierservice.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for handling operations related to categories.
 * This class implements business logic for CRUD operations.
 *
 *
 * @see org.springframework.stereotype.Service
 * @see lombok.extern.slf4j.Slf4j
 * @see CategoryRepository
 */
@Slf4j
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Constructs a new CategoryService with the specified CategoryRepository.
     *
     * @param categoryRepository the repository for accessing and managing category data
     */
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Creates a new category based on the provided category DTO.
     *
     * @param categoryDto the DTO containing information about the category to be created
     * @return the created category
     * @throws CategoryServiceException if an error occurs while creating the category
     */
    public Category createCategory(CategoryDto categoryDto) {
        try {
            if (categoryRepository.existsById(categoryDto.getId())) {
                throw new CategoryServiceException("Category with id "
                                                   + categoryDto.getId()
                                                   + " already exists");
            }

            Category newCategory = new Category();
            newCategory.setName(categoryDto.getName());
            newCategory.setProducts(categoryDto.getProducts());

            return categoryRepository.save(newCategory);
        } catch (CategoryServiceException ex) {
            log.error("Failed to create category: " + ex.getMessage());
            throw new CategoryServiceException("Failed to create category: "
                                               + ex.getMessage());
        }
    }

    /**
     * Get a list of all category.
     *
     * @return a list of category
     * @throws CategoryServiceException if an error occurs while getting list
     */
    public List<Category> getAllCategories() {
        try {
            return categoryRepository.findAll();
        } catch (CategoryServiceException ex) {
            log.error("Failed to getAllCategories: " + ex.getMessage());
            throw new CategoryServiceException("Failed to getAllCategories: "
                                               + ex.getMessage());
        }
    }

    /**
     * Get category by id.
     *
     * @param id the id of category to be got
     * @return the gotten category
     * @throws CategoryServiceException if an error occurs while creating the category
     */
    public Category getCategoryById(Long id) {
        try {
            return categoryRepository.findById(id).orElse(null);
        } catch (CategoryServiceException ex) {
            log.error("Failed to findById: " + ex.getMessage());
            throw new CategoryServiceException("Failed to findById: "
                                               + ex.getMessage());
        }
    }

    /**
     * Updates category based on the provided category DTO and id.
     *
     * @param id the id of category to be updated
     * @param categoryDto the DTO containing information about the category to be updated
     * @return the updated category
     * @throws CategoryServiceException if an error occurs while updating the category
     */
    public Category updateCategory(Long id, CategoryDto categoryDto) {
        try {
            Category category = getCategoryById(id);

            if (category != null) {
                category.setName(categoryDto.getName());
                return categoryRepository.save(category);
            }
        } catch (CategoryServiceException ex) {
            log.error("Failed to updateCategory: " + ex.getMessage());
            throw new CategoryServiceException("Failed to updateCategory: "
                                               + ex.getMessage());
        }

        return null;
    }

    /**
     * Deletes a category with the specified ID.
     *
     * @param id the ID of the category to delete
     * @throws CategoryServiceException if an error occurs while deleting the category
     */
    public void deleteCategory(Long id) {
        try {
            Category category = getCategoryById(id);

            if (category != null) {
                categoryRepository.delete(category);
            }
        } catch (CategoryServiceException ex) {
            log.error("Failed to deleteCategory: " + ex.getMessage());
            throw new CategoryServiceException("Failed to deleteCategory: "
                                               + ex.getMessage());
        }
    }
}
