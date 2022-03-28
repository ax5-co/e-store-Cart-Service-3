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

import com.execise.estore.estore.entity.Brand;
import com.execise.estore.estore.repository.BrandRepository;

@RestController
@RequestMapping("/api/cat/brands")
public class BrandController {
	@Autowired
	private BrandRepository brandRepository;

	
	
	// Return the Brand Banner Image from the database using ResponseEntity
	@GetMapping("/{id}/bannerImage")
	public ResponseEntity<byte[]> fromDatabaseAsResEntity(@PathVariable("id") Long id) throws SQLException {

		
		Optional<Brand> foundBarnd = brandRepository.findById(id);
		byte[] imageBytes = null;
		if (foundBarnd.isPresent()) {

			// imageBytes = foundBarnd.get().getBannerImage().getBytes(1,
			// (int) foundBarnd.get().getBannerImage().length());
			imageBytes = foundBarnd.get().getBannerImage();
		}

		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
	}

}
