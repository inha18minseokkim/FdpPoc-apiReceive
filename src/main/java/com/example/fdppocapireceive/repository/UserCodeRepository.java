package com.example.fdppocapireceive.repository;

import com.example.fdppocapireceive.entity.UserCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCodeRepository extends JpaRepository<UserCode,Long> {
    Optional<UserCode> findByCodeDetailName(String codeDetailName);
}
