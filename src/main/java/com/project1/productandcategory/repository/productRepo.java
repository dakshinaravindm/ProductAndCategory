package com.project1.productandcategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project1.productandcategory.model.product;

@Repository
public interface productRepo extends JpaRepository<product, Long> {
}