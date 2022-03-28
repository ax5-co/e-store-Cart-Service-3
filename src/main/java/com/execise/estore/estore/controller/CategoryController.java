package com.execise.estore.estore.controller;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.execise.estore.estore.entity.Category;
import com.execise.estore.estore.repository.CategoryRepository;

@RestController
@RequestMapping("api/cat/categories")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;

	
	
	// Return the Category Banner Image from the database using ResponseEntity
	@GetMapping("{id}/bannerImage")
	public ResponseEntity<byte[]> fromDatabaseAsResEntity(@PathVariable("id") Long id) throws SQLException {

		
		Optional<Category> foundCategory = categoryRepository.findById(id);
		byte[] imageBytes = null;
		if (foundCategory.isPresent()) {

			// imageBytes = foundBarnd.get().getBannerImage().getBytes(1,
			// (int) foundBarnd.get().getBannerImage().length());
			imageBytes = foundCategory.get().getBannerImage();
		}

		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
	}
}
