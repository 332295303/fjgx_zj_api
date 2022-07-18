package com.inspur.zzy.fjgx.sfrz.core.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "GSPUser")
public class FJGSPUserEntity {
    @Id
    private String id;
    private String code;
    @Column(name = "name_chs")
    private String name;
}
