package com.example.fdppocapireceive.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class EmptyResponseDTO {
    @JsonProperty("condition")
    private List<ConditionDTO> condition;

    @JsonProperty("data")
    private List<String> data;
}
