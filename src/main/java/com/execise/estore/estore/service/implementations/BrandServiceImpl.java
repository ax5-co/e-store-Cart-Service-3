package com.execise.estore.estore.service.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.execise.estore.estore.entity.Brand;
import com.execise.estore.estore.repository.BrandRepository;

@Service
@Transactional
public class BrandServiceImpl {
	
	@Autowired
	private BrandRepository brandRepository;
	
	public void saveImage(Brand brand) {
		brandRepository.save(brand);	
	}

	public List<Brand> getAllBrands() {
		return brandRepository.findAll();
	}

	public Optional<Brand> getBrandById(Long id) {
		return brandRepository.findById(id);
	}

}
