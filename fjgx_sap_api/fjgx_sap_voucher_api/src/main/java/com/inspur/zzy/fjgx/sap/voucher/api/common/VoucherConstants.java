package com.inspur.zzy.fjgx.sap.voucher.api.common;

public class VoucherConstants {
    private VoucherConstants(){
        //FileSystems.getDefault().newWatchService()
    }
    public static final String GS="SYS02";
    public static final String SAP="SAP";
    //api发布前缀
    public static final String API_PREFIX ="/zzy/fjgx/v1.0/sap/voucher";
    //异常前缀
    public static final String EXCEPTION_PREFIX="ZZY_FJGX_SAP_VOUCHER";

    public static class Ossi{
        public static final String OSSI="ossi";

        public static final String SUCCESS="1";
        public static final String FAIL="0";

        public static final String ASYNC="1";
        public static final String SYNC="0";

        public static final String PUSH="push";
        public static final String CANCEL="cancel";
    }

    public static class Sap{
        public static final String SUCCESS="S";
        public static final String FAIL="E";
    }

    public static class RpcService{
        public static final String backWriteByErp="com.inspur.zzy.ossi.vouchermapper.rpc.parkingdocumentservice.backWriteByErp";
        public static final String backWriteByErpForCancel="com.inspur.zzy.ossi.vouchermapper.rpc.parkingdocumentservice.backWriteByErpForCancel";
    }

    public static class RpcPublish{
        public static final String APP="zzy";
        public static final String SU="fjgx";
    }
}
