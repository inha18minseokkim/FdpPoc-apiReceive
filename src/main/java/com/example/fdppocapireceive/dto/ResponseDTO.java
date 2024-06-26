package com.example.fdppocapireceive.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    @JsonProperty("condition")
    private List<ConditionDTO> condition;

    @JsonProperty("data")
    private DataDTO data;
}
