package com.example.fdppocapireceive.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"categoryCode","itemCode","kindCode","classCode","gradeCode"})})
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
    private String gradeCode;            //pd_grade_cd
    private String unitName;            //snog_unit_nm
    private Float unitValue;            //snog_unit_val
    private Boolean isAvailable;            //사용여부
    private String categoryName;
    private String itemName;
    private String kindName;
    private String gradeName;
    @OneToMany(mappedBy = "baseProduct",fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<InnerProduct> innerProducts;
}
