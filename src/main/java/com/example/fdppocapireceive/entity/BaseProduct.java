package com.example.fdppocapireceive.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(name="productUnique",columnNames = {"categoryCode","itemCode","kindCode","classCode","rankCode"})})
public class BaseProduct {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 3)
    private String categoryCode;    //pd_ctgr_cd
    @Column(length= 4)
    private String itemCode;            //pd_lsar_cd
    @Column(length= 3)
    private String kindCode;            //pd_knd_cd
    @Column(length=2)
    private String classCode;           //whls_rtl_dcd
    @Column(length=2)
    private String rankCode;            //pd_grade_cd
    private String unitName;            //snog_unit_nm
    private Float unitValue;            //snog_unit_val
    private Boolean isAvailable;            //사용여부
    private String categoryName;
    private String itemName;
    private String kindName;
    private String gradeName;
    @OneToMany(mappedBy = "baseProduct",fetch = FetchType.LAZY)
    private List<InnerProduct> innerProducts;
}
