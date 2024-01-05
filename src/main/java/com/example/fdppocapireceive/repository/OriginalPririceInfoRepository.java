package com.example.fdppocapireceive.repository;

import com.example.fdppocapireceive.entity.OriginalPriceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OriginalPririceInfoRepository extends JpaRepository<OriginalPriceInfo,Long> {
}
