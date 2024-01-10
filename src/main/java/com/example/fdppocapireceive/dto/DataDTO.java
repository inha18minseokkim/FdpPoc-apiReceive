package com.example.fdppocapireceive.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataDTO {
    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("item")
    private List<ItemDTO> items;
}
