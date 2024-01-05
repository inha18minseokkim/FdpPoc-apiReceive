package com.example.fdppocapireceive.dto;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {
    private String pCertKey;
    private String pCertId;
    private String pReturntype;
    private String pStartday;
    private String pEndday;
    private String pConvertKgYn;
    private String pItemcategorycode;
    private String pItemcode;
    private String pKindcode;
    private String pProductrankcode;
    private String pCountrycode;
    private String pProductclscode;
}
