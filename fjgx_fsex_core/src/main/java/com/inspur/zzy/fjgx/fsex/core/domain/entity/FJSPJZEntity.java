package com.inspur.zzy.fjgx.fsex.core.domain.entity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "FJSPJSJZ")
public class FJSPJZEntity {
    @Id
    private String id;
    private String xzgsid;
    private String xzbmid;
    private String jd1id;
    private String jd2id;
    private String jd3id;
    private String jd4id;
    private String jd5id;
    private String jd6id;
    private String jd7id;
    private String jd8id;
    private String jd9id;
    private String jd10id;
    private String jd11id;
    private String jd12id;
    private String jd13id;
    private String jd14id;
    private String jd15id;
}
