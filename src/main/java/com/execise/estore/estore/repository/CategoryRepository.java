package com.execise.estore.estore.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.execise.estore.estore.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findTop1ByTitleContaining(String infixTitle);
}
