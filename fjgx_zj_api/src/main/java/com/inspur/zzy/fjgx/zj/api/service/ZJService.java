package com.inspur.zzy.fjgx.zj.api.service;

import com.inspur.zzy.fjgx.zj.api.entity.ZJBackInfo;
import com.inspur.zzy.fjgx.zj.api.entity.ZJResult;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ZJService {
    @POST
    @Path("/back")
    ZJResult zjBackService(ZJBackInfo zjBackInfo);

    @GET
    @Path("/show")
    ZJResult zjTest();

    @POST
    @Path("/writeback")
    ZJResult zjWriteBackService(ZJBackInfo zjBackInfo);
}
