package com.execise.estore.estore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.execise.estore.estore.entity.Product;
import com.execise.estore.estore.entity.ProductVariant;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

	Page<ProductVariant> findAll(Pageable pageable);
	
	List<ProductVariant> findAll(Sort sort);
	
	Page<ProductVariant> findById(Long id, Pageable pageable);
	
	Page<ProductVariant> findAllByProduct(Product product, Pageable pageable);
	
	Page<ProductVariant> findAllBySku(String sku, Pageable pageable);
	
	Page<ProductVariant> findAllBySkuContaining(String sku, Pageable pageable);
	
	Page<ProductVariant> findBySkuContaining(String sku, Pageable pageable);
	
	Page<ProductVariant> findAllBySize(String size, Pageable pageable);
	
	Optional<ProductVariant> findByProductAndSizeEquals(Product product, String size);
}
