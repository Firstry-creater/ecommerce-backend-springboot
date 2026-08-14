package com.example.ecommercebackend.service;

import com.example.ecommercebackend.entity.Category;
import com.example.ecommercebackend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private  final CategoryRepository categoryRepository;

    //create category
    public Category createCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())){
            throw  new RuntimeException("Category is  already exist");
        }
        return categoryRepository.save(category);
    }

    // Get All Categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // get category by id
    public  Category getCategoryById(Long id){
        return  categoryRepository.findById(id)
                .orElseThrow(()->
                        new RuntimeException("Category not found"));
    }

    //Delete Category
    public  void deleteCategory(Long id){

        categoryRepository.deleteById(id);
    }
}
