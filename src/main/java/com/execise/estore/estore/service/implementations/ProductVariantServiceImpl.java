package com.execise.estore.estore.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.execise.estore.estore.entity.ProductVariant;
import com.execise.estore.estore.repository.ProductVariantRepository;
import com.execise.estore.estore.service.ProductVariantService;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {

	@Autowired
	private ProductVariantRepository productVariantRepository;
	
	@Override
	public ProductVariant getVariantBySku(String sku) {
		return productVariantRepository.findBySkuContaining(sku, null).getContent().get(0);
	}

	@Override
	public void save(ProductVariant productVariant) {
		productVariantRepository.save(productVariant);
		
	}
}
