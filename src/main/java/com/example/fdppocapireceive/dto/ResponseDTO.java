package com.example.fdppocapireceive.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ResponseDTO {
    @JsonProperty("condition")
    private List<ConditionDTO> condition;

    @JsonProperty("data")
    private DataDTO data;
}
