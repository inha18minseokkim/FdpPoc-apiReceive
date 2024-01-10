package com.example.fdppocapireceive.api;

import com.example.fdppocapireceive.config.ObjectMapperConfig;
import com.example.fdppocapireceive.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class ApiReceive {
    @Value("${KAMIS_ID}")
    private String kamisId;
    @Value("${KAMIS_SECRET}")
    private String kamisSecret;
    private final ObjectMapper objectMapper;
    public ResponseDTO RequestFromServer(RequestDTO requestDTO) {


        String baseUrl = "https://www.kamis.or.kr/service/price/xml.do";

        WebClient webClient = WebClient.builder()
                .build();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("action", "periodProductList")
                .queryParam("p_cert_key", requestDTO.getPCertKey())
                .queryParam("p_cert_id", requestDTO.getPCertId())
                .queryParam("p_returntype", requestDTO.getPReturntype())
                .queryParam("p_startday", requestDTO.getPStartday())
                .queryParam("p_endday", requestDTO.getPEndday())
                .queryParam("p_convert_kg_yn", requestDTO.getPConvertKgYn())
                .queryParam("p_itemcategorycode", requestDTO.getPItemcategorycode())
                .queryParam("p_itemcode", requestDTO.getPItemcode())
                .queryParam("p_kindcode", requestDTO.getPKindcode())
                .queryParam("p_productrankcode", requestDTO.getPProductrankcode())
                .queryParam("p_countrycode", requestDTO.getPCountrycode())
                .queryParam("p_productclscode", requestDTO.getPProductclscode());

        String finalUrl = builder.toUriString();
        log.debug("request 요청 시작 : {}",finalUrl);

        Mono<String> dataWrapperMono = webClient.get()
                .uri(finalUrl)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext((response) -> log.debug("응답 데이터 : {}",response.toString()));

        ResponseDTO dataWrapper = dataWrapperMono.block().transform((element) -> {
            try {
                return objectMapper.readValue(element, ResponseDTO.class);
            } catch (JsonProcessingException e) {
                try{
                    EmptyResponseDTO emptyResponseDTO = objectMapper.readValue(element, EmptyResponseDTO.class);
                    log.error("빈 객체 데이터 리턴 : {}",emptyResponseDTO);
                    return ResponseDTO.builder()
                            .data(DataDTO.builder().items(List.of()).errorCode("001").build())
                            .condition(List.of(ConditionDTO.builder().build()))
                            .build();
                } catch(JsonProcessingException e1){
                    log.error("url 호출 후 매핑 시 에러 발생 : {}",finalUrl);
                    e.printStackTrace();
                    throw new RuntimeException(e1);
                }
            }
        }); // block to get the result, handle this properly in a non-blocking environment


        // Now you can work with the DataWrapper object
        if (dataWrapper != null) {
            ConditionDTO conditionDTO = dataWrapper.getCondition().get(0);
            DataDTO dataDTO = dataWrapper.getData();

            // Process the condition and data as needed
            log.debug("Received ConditionDTO: " + conditionDTO);
            log.debug("Received DataDTO: " + dataDTO);
        } else {
            log.debug("Failed to retrieve DataWrapper");
        }
        return dataWrapper;

//        Mono<String> dataWrapperMono = webClient.get()
//                .uri(finalUrl)
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .onStatus(status -> status.is3xxRedirection(),(response) -> {
//                    log.info("리디렉션 : {}" ,response.headers().asHttpHeaders());
//                    List<String> location = response.headers().header("location");
//                    log.info(location.toString());
//                    return Mono.empty();
//                })
//                .bodyToMono(String.class).doOnNext((response) -> log.info("응답 데이터 : {}",response.toString()));
//
//        String dataWrapper = dataWrapperMono.block(); // block to get the result, handle this properly in a non-blocking environment
//        log.info(dataWrapper);
//        return null;

    }

}
