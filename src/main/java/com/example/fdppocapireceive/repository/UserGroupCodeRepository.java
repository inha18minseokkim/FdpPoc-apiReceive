package com.example.fdppocapireceive.repository;

import com.example.fdppocapireceive.entity.UserGroupCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupCodeRepository extends JpaRepository<UserGroupCode,String> {
    List<UserGroupCode> findAllByCodeDetailNameAndUseInfo(String codeDetailName,Boolean useInfo);
}
