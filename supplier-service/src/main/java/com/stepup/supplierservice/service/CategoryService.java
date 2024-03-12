package com.stepup.supplierservice.service;

import com.stepup.supplierservice.dto.CategoryDto;
import com.stepup.supplierservice.entity.Category;
import com.stepup.supplierservice.exception.CategoryServiceException;
import com.stepup.supplierservice.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

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

    public List<Category> getAllCategories() {
        try {
            return categoryRepository.findAll();
        } catch (CategoryServiceException ex) {
            log.error("Failed to getAllCategories: " + ex.getMessage());
            throw new CategoryServiceException("Failed to getAllCategories: "
                                               + ex.getMessage());
        }
    }

    public Category getCategoryById(Long id) {
        try {
            return categoryRepository.findById(id).orElse(null);
        } catch (CategoryServiceException ex) {
            log.error("Failed to findById: " + ex.getMessage());
            throw new CategoryServiceException("Failed to findById: "
                                               + ex.getMessage());
        }
    }

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
