package com.example.fdppocapireceive.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConditionDTO {
    @JsonProperty("p_startday")
    private String pStartday;

    @JsonProperty("p_endday")
    private String pEndday;

    @JsonProperty("p_itemcategorycode")
    private String pItemCategoryCode;

    @JsonProperty("p_itemcode")
    private String pItemCode;

    @JsonProperty("p_kindcode")
    private String pKindCode;

    @JsonProperty("p_productrankcode")
    private String pProductRankCode;

    @JsonProperty("p_countycode")
    private String pCountyCode;

    @JsonProperty("p_convert_kg_yn")
    private String pConvertKgYn;

    @JsonProperty("p_key")
    private String pKey;

    @JsonProperty("p_id")
    private String pId;

    @JsonProperty("p_returntype")
    private String pReturnType;

}