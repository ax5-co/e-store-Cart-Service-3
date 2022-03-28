package com.execise.estore.estore.service;

import org.springframework.stereotype.Service;

import com.execise.estore.estore.entity.ProductVariant;

@Service
public interface ProductVariantService {

	ProductVariant getVariantBySku(String sku);

	void save(ProductVariant productVariant);

}