package com.example.imdb_clone.service;

import com.example.imdb_clone.dto.CategoryDto;
import com.example.imdb_clone.entity.Category;
import com.example.imdb_clone.exception.ResourceNotFoundException;
import com.example.imdb_clone.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kategori bulunamadı: " + id));
        return toDto(category);
    }

    public CategoryDto createCategory(CategoryDto dto) {
        Optional<Category> existing = categoryRepository.findByNameIgnoreCase(dto.getName().trim());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Bu isimde bir kategori zaten var.");
        }
        Category category = toEntity(dto);
        Category saved = categoryRepository.save(category);
        return toDto(saved);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kategori bulunamadı: " + id));

        categoryRepository.delete(category);
    }

    private CategoryDto toDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    private Category toEntity(CategoryDto dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName().trim());
        return category;
    }

}
