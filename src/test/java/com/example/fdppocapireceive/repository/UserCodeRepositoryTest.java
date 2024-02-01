package com.example.fdppocapireceive.repository;

import com.example.fdppocapireceive.api.ApiReceive;
import com.example.fdppocapireceive.entity.UserCode;
import com.example.fdppocapireceive.entity.UserGroupCode;
import com.example.fdppocapireceive.job.ApiReceiveJob;
import com.example.fdppocapireceive.job.ReceiveTasklet;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class UserCodeRepositoryTest {
    @Autowired
    UserGroupCodeRepository userGroupCodeRepository;
    @Autowired
    UserCodeRepository userCodeRepository;
    @MockBean
    ApiReceiveJob apiReceiveJob;
    @MockBean
    ApiReceive apiReceive;
    @MockBean
    ReceiveTasklet receiveTasklet;
    @Test
    @Transactional
    void readTest() {
        UserGroupCode kamisApiRegionCodes = userGroupCodeRepository.findById("FDPREGN1101").get();
        List<UserCode> userCodes = kamisApiRegionCodes.getUserCodes();
        log.info("결과 : {}", userCodes);
        Optional<UserCode> seoul = userCodeRepository.findById("1101");
        log.info("결과 : {}",seoul);
        Assertions.assertThat(userCodes).contains(seoul.get());
    }
}