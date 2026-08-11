package com.ecommerce.service.impl;

import com.ecommerce.dto.CategoryRequest;
import com.ecommerce.dto.CategoryResponse;
import com.ecommerce.entity.Category;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.service.CategoryService;
import com.ecommerce.service.FileUploadService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final FileUploadService uploadService;
  private final CategoryRepository categoryRepository;

  @Override
  public CategoryResponse createCategory(CategoryRequest request) {
    try {
      if (categoryRepository.existsByName(request.getName())) {
        throw new RuntimeException(
            "Category with name '" + request.getName() + "' already exists.");
      }

      // Handle file upload safely
      if (request.getThumbnail() != null && !request.getThumbnail().isEmpty()) {

        final String fileName = uploadService.storeFile(request.getThumbnail());

        String fileDownloadUri =
            ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(fileName)
                .toUriString();
        Category category =
            Category.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .thumbnail(fileDownloadUri)
                .description(request.getDescription())
                .build();
        Category savedCategory = categoryRepository.save(category);
        return mapToResponse(savedCategory);
      } else {
        throw new BadRequestException("File upload failed.");
      }
    } catch (Exception e) {
      throw new BadRequestException("File upload error: " + e.getMessage());
    }
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
    category.setSlug(request.getSlug());
    category.setThumbnail(uploadService.storeFile(request.getThumbnail()));
    category.setDescription(request.getDescription());

    if (request.getThumbnail() != null && !request.getThumbnail().isEmpty()) {

      final String fileName = uploadService.storeFile(request.getThumbnail());

      String fileDownloadUri =
          ServletUriComponentsBuilder.fromCurrentContextPath()
              .path("/uploads/")
              .path(fileName)
              .toUriString();

      category.setThumbnail(fileDownloadUri);
    }
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
        .slug(category.getSlug())
        .thumbnail(category.getThumbnail())
        .description(category.getDescription())
        .build();
  }
}
