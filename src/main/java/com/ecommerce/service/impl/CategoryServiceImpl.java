package com.ecommerce.service.impl;

import com.ecommerce.dto.CategoryRequest;
import com.ecommerce.dto.CategoryResponse;
import com.ecommerce.entity.Category;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.service.CategoryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  @Override
  public CategoryResponse createCategory(CategoryRequest request) {
    if (categoryRepository.existsByName(request.getName())) {
      throw new RuntimeException("Category with name '" + request.getName() + "' already exists.");
    }

    Category category =
        Category.builder()
            .name(request.getName())
            .slug(request.getSlug())
            .description(request.getDescription())
            .build();

    Category savedCategory = categoryRepository.save(category);
    return mapToResponse(savedCategory);
  }

  @Override
  public List<CategoryResponse> getAllCategories() {
    return categoryRepository.findAll().stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Override
  public CategoryResponse getCategoryById(Long id) {
    Category category =
        categoryRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    return mapToResponse(category);
  }

  @Override
  public CategoryResponse updateCategory(Long id, CategoryRequest request) {
    Category category =
        categoryRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

    category.setName(request.getName());
    category.setDescription(request.getDescription());

    Category updatedCategory = categoryRepository.save(category);
    return mapToResponse(updatedCategory);
  }

  @Override
  public void deleteCategory(Long id) {
    if (!categoryRepository.existsById(id)) {
      throw new RuntimeException("Category not found with id: " + id);
    }
    categoryRepository.deleteById(id);
  }

  private CategoryResponse mapToResponse(Category category) {
    return CategoryResponse.builder()
        .id(category.getId())
        .name(category.getName())
        .description(category.getDescription())
        .build();
  }
}
