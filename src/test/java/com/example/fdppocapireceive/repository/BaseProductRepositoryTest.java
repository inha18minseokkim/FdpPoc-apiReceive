package com.example.fdppocapireceive.repository;

import com.example.fdppocapireceive.FdpPocApiReceiveApplication;
import com.example.fdppocapireceive.api.ApiReceive;
import com.example.fdppocapireceive.entity.BaseProduct;
import com.example.fdppocapireceive.job.ApiReceiveJob;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class BaseProductRepositoryTest {
    @Autowired
    BaseProductRepository repository;
    @MockBean
    ApiReceiveJob apiReceiveJob;
    @MockBean
    ApiReceive apiReceive;
    @Test
    @Transactional
    void findByIsAvailableTest() {
        List<BaseProduct> result = repository.findAllByIsAvailableEquals(true);
        BaseProduct baseProduct = result.get(0);
        log.info("결과 : {}", baseProduct);
        Assertions.assertEquals(baseProduct.getIsAvailable(),true);
    }
    @Test
    @Transactional
    void insertBaseProduct() {
        BaseProduct tempBaseProduct = BaseProduct.builder()
                .categoryCode("100")
                .itemCode("152")
                .kindCode("01")
                .classCode("01")
                .gradeCode("04")
                .categoryName("식량작물")
                .itemName("감자")
                .kindName("수미(노지)")
                .gradeName("상품")
                .unitName("kg")
                .unitValue(1F)
                .isAvailable(true)
                .build();
        repository.save(tempBaseProduct);

    }
}

