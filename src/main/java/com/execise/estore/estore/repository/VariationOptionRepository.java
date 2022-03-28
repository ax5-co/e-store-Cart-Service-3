package com.execise.estore.estore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.execise.estore.estore.entity.VariationOption;
@Repository
public interface VariationOptionRepository extends JpaRepository<VariationOption, Long> {

}
