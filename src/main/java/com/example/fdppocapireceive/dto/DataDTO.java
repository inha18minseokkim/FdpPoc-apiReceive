package com.example.fdppocapireceive.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class DataDTO {
    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("item")
    private List<ItemDTO> items;
}
