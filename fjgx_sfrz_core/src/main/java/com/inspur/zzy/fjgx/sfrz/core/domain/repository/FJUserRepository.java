package com.inspur.zzy.fjgx.sfrz.core.domain.repository;

import com.inspur.zzy.fjgx.sfrz.core.domain.entity.FJGSPUserEntity;
import io.iec.edp.caf.data.orm.DataRepository;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.boot.autoconfigure.domain.EntityScan;

public interface FJUserRepository extends DataRepository<FJGSPUserEntity, String>{
    FJGSPUserEntity findByCode(@Param("code") String userCode);
}
