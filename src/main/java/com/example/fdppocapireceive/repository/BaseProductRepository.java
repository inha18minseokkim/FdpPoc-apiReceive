package com.example.fdppocapireceive.repository;

import com.example.fdppocapireceive.entity.BaseProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseProductRepository extends JpaRepository<BaseProduct,Long> {
    List<BaseProduct> findAllByIsAvailableEquals(Boolean isAvailable);
}

