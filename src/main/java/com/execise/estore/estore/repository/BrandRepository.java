package com.execise.estore.estore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.execise.estore.estore.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

	Brand findTop1ByTitleContaining(String infixTitle);
}
