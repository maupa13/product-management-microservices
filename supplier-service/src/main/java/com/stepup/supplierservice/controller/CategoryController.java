package com.stepup.supplierservice.controller;

import com.stepup.supplierservice.dto.CategoryDto;
import com.stepup.supplierservice.entity.Category;
import com.stepup.supplierservice.service.CategoryService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class responsible for handling HTTP requests related to categories.
 * This class is annotated with {@link org.springframework.web.bind.annotation.RestController} to indicate
 * that it processes incoming RESTful requests and produces JSON responses.
 * The base path for all request mappings in this controller is "/categories", specified by the
 * {@link org.springframework.web.bind.annotation.RequestMapping} annotation.
 *
 * @see org.springframework.web.bind.annotation.RestController
 * @see org.springframework.web.bind.annotation.RequestMapping
 * @see CategoryService
 * @see org.springframework.web.bind.annotation.PostMapping
 * @see org.springframework.web.bind.annotation.GetMapping
 * @see org.springframework.web.bind.annotation.PutMapping
 * @see org.springframework.web.bind.annotation.DeleteMapping
 * @see org.springframework.web.bind.annotation.RequestBody
 * @see org.springframework.web.bind.annotation.PathVariable
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Constructs a new CategoryController with the specified CategoryService.
     *
     * @param categoryService the service responsible for handling category-related operations
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Handles HTTP POST requests to create a new category.
     *
     * @param categoryDto the CategoryDto object representing the new category
     * @return the created Category object
     */
    @PostMapping
    public Category createCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.createCategory(categoryDto);
    }

    /**
     * Handles HTTP GET requests to retrieve all categories.
     *
     * @return a list of Category objects representing all categories
     */
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    /**
     * Handles HTTP GET requests to retrieve a category by its ID.
     *
     * @param id the ID of the category to retrieve
     * @return the Category object with the specified ID
     */
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    /**
     * Handles HTTP PUT requests to update a category by its ID.
     *
     * @param id the ID of the category to update
     * @param categoryDto the CategoryDto object representing the updated category
     * @return the updated Category object
     */
    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id,
                                   @RequestBody CategoryDto categoryDto) {
        return categoryService.updateCategory(id, categoryDto);
    }

    /**
     * Handles HTTP DELETE requests to delete a category by its ID.
     *
     * @param id the ID of the category to delete
     */
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
