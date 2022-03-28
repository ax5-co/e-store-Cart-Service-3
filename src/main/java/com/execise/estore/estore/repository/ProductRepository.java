package com.execise.estore.estore.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.execise.estore.estore.entity.Brand;
import com.execise.estore.estore.entity.Category;
import com.execise.estore.estore.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	/*
	APIs -> catalog
	Open -  not authenticated 
	all product list 
	new products 
	featured products 
	products per category 
	products per brand 
	all products sorted by price low

	all products sorted by price high

	** all listing APIs must be paginated **
	
	*/
	 Page<Product> findAll(Pageable pageable);
	
	 List<Product> findAll(Sort sort);
	
	 Page<Product> findAllByIsNew(boolean isNew, Pageable pageable);
	
	 Page<Product> findAllByIsFeatured(boolean isFeatured, Pageable pageable);
	
	 Page<Product> findAllByCategory(Category category, Pageable pageable);
	
	 Page<Product> findAllByBrand(Brand brand, Pageable pageable);
	
	 Page<Product> findAllByPrice(BigDecimal price, Pageable pageable);

	 Page<Product> findByTitleContaining(String title, Pageable paging);
	 
	 Optional<Product> findByTitleEqualsAndColorEquals(String title, String color);
	
}
