package com.example.fdppocapireceive.repository;

import com.example.fdppocapireceive.entity.BaseProduct;
import com.example.fdppocapireceive.entity.OriginalPriceInfo;
import com.example.fdppocapireceive.entity.UserCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OriginalPririceInfoRepository extends JpaRepository<OriginalPriceInfo,Long> {
    @Query("select o " +
            "from OriginalPriceInfo o " +
            "where o.baseDate = :baseDate and o.baseProduct = :baseProduct and o.regionInfo = :regionInfo and o.storeName = :storeName")
    Optional<OriginalPriceInfo> findOriginalPriceInfoByUnique
            (@Param("baseDate")String baseDate,
             @Param("baseProduct") BaseProduct baseProduct,
             @Param("regionInfo") UserCode regionInfo,
             @Param("storeName") String storeName);
}
