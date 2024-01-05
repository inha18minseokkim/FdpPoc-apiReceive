package com.example.fdppocapireceive.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.ToString;

import java.lang.reflect.Array;
import java.util.Arrays;

@Data
@ToString
public class ItemDTO {
    @JsonProperty("itemname")
    private String itemname;

    @JsonProperty("kindname")
    private String kindname;

    @JsonProperty("countyname")
    private String countyname;

    @JsonProperty("marketname")
    private String marketname;

    @JsonProperty("yyyy")
    private String yyyy;

    @JsonProperty("regday")
    private String regday;

    @JsonProperty("price")
    private String price;

    @JsonSetter
    public void setItemname(Object value){
        itemname = value.toString();
    }
    @JsonSetter
    public void setKindname(Object value){
        kindname = value.toString();
    }
    @JsonSetter
    public void setMarketname(Object value){
        marketname = value.toString();
    }
}
