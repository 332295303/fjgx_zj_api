package com.inspur.zzy.fjgx.fsex.core.domain.repository;

import com.inspur.zzy.fjgx.fsex.core.domain.entity.FJSPJZEntity;
import io.iec.edp.caf.data.orm.DataRepository;
import io.lettuce.core.dynamic.annotation.Param;
public interface FJSPJZRepository extends DataRepository<FJSPJZEntity, String>{
    FJSPJZEntity findByXzbmid(@Param("xzbmid") String xzbmid);
}
