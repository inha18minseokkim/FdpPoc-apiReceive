package com.example.fdppocapireceive.repository;

import com.example.fdppocapireceive.entity.UserCode;
import com.example.fdppocapireceive.entity.UserGroupCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCodeRepository extends JpaRepository<UserCode,String> {
    List<UserCode> findAllByUserGroupCodeAndUseInfo(UserGroupCode userGroupCode, Boolean useInfo);

    Optional<UserCode> findByCodeDetailName(String codeDetailName);
}
