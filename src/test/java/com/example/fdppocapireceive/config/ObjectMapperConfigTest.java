package com.example.fdppocapireceive.config;

import com.example.fdppocapireceive.dto.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
class ObjectMapperConfigTest {
    @Test
    void mappingTest() throws JsonProcessingException {
        String target = "{\"condition\":[{\"p_startday\":\"2024-01-03\",\"p_endday\":\"2024-01-03\",\"p_itemcategorycode\":\"200\",\"p_itemcode\":\"212\",\"p_kindcode\":\"00\",\"p_productrankcode\":\"04\",\"p_countycode\":\"1101\",\"p_convert_kg_yn\":\"Y\",\"p_key\":\"abef9f44-e264-4f56-a3d7-6ab6f0beaf82\",\"p_id\":\"3287\",\"p_returntype\":\"json\"}],\"data\":{\"error_code\":\"000\",\"item\":[{\"itemname\":[],\"kindname\":[],\"countyname\":\"평균\",\"marketname\":[],\"yyyy\":\"2024\",\"regday\":\"01/03\",\"price\":\"4,165\"},{\"itemname\":[],\"kindname\":[],\"countyname\":\"평년\",\"marketname\":[],\"yyyy\":\"2024\",\"regday\":\"01/03\",\"price\":\"3,685\"},{\"itemname\":\"양배추\",\"kindname\":\"양배추(1포기)\",\"countyname\":\"서울\",\"marketname\":\"경동\",\"yyyy\":\"2024\",\"regday\":\"01/03\",\"price\":\"3,830\"},{\"itemname\":\"양배추\",\"kindname\":\"양배추(1포기)\",\"countyname\":\"서울\",\"marketname\":\"복조리\",\"yyyy\":\"2024\",\"regday\":\"01/03\",\"price\":\"4,830\"},{\"itemname\":\"양배추\",\"kindname\":\"양배추(1포기)\",\"countyname\":\"서울\",\"marketname\":\"B-유통\",\"yyyy\":\"2024\",\"regday\":\"01/03\",\"price\":\"2,880\"},{\"itemname\":\"양배추\",\"kindname\":\"양배추(1포기)\",\"countyname\":\"서울\",\"marketname\":\"B`-유통\",\"yyyy\":\"2024\",\"regday\":\"01/03\",\"price\":\"4,980\"},{\"itemname\":\"양배추\",\"kindname\":\"양배추(1포기)\",\"countyname\":\"서울\",\"marketname\":\"E-유통\",\"yyyy\":\"2024\",\"regday\":\"01/03\",\"price\":\"3,490\"},{\"itemname\":\"양배추\",\"kindname\":\"양배추(1포기)\",\"countyname\":\"서울\",\"marketname\":\"I-유통\",\"yyyy\":\"2024\",\"regday\":\"01/03\",\"price\":\"4,980\"}]}}";
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseDTO responseDTO = objectMapper.readValue(target, ResponseDTO.class);
        log.info("결과 : {}" ,responseDTO);
    }

}