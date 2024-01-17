package com.example.team5_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.team5_project.model.CategoryName;

public interface CategoryNameRepository extends JpaRepository<CategoryName, Long> {

	Optional<CategoryName> findByCategoryName(String category);


}
