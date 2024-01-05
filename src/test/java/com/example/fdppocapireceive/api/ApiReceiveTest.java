package com.example.fdppocapireceive.api;

import com.example.fdppocapireceive.config.ObjectMapperConfig;
import com.example.fdppocapireceive.dto.RequestDTO;
import com.example.fdppocapireceive.dto.ResponseDTO;
import com.example.fdppocapireceive.repository.BaseProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.batch.BatchDataSourceScriptDatabaseInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
@ContextConfiguration(classes = {ApiReceive.class, ObjectMapperConfig.class})
class ApiReceiveTest {
    @Autowired
    ApiReceive apiReceive;

    @Test
    void apiReceiveTest() {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setPCertKey("abef9f44-e264-4f56-a3d7-6ab6f0beaf82");
        requestDTO.setPCertId("3287");
        requestDTO.setPReturntype("json");
        requestDTO.setPStartday("2024-01-03");
        requestDTO.setPEndday("2024-01-03");
        requestDTO.setPConvertKgYn("Y");
        requestDTO.setPItemcategorycode("200");
        requestDTO.setPItemcode("212");
        requestDTO.setPKindcode("00");
        requestDTO.setPProductrankcode("04");
        requestDTO.setPCountrycode("1101");
        requestDTO.setPProductclscode("01");

        ResponseDTO responseDTO = apiReceive.RequestFromServer(requestDTO);
        log.info(responseDTO.toString());
    }


}