package com.minmai.wallet.common.constant;

/**
 * 网络请求地址
 */
public class Constant {

    //host 地址
    private static final String URL="http://192.168.1.3:8080/";

    // public static final String URL = "http://admin.zhonghuatech.com/";
    //  public static final String URL = "http://admin.minmai1688.com/";

    //项目名
     private static final String PROJECT="qike/app/";

    //项目名
    //private static final String PROJECT="app/";

    //请求地址
    public static final String BASE_URL=URL+PROJECT;


    //银行卡url
    public static final String BANK_CARD_URL="https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardBinCheck=true&cardNo=";

    //信用卡申请地址
    public static final String CREDIT_CARD_URL="https://creditcard.cmbc.com.cn/wsv2/?enstr=pMm5%2bKPh2tpP63yxiL5NRWjnJJcNY%2fv5fz309Dt16ARX5ue19njSGY169YlvP0DPpUyv8TtNOdocQ2uHvVBAzXd%2f%2fRITreLq9n4XwLrfoh%2fwKKwLhP%2bbtICi8wiUCR4o4K7nhyYOcUHfUzcZc1%2fCXr2fPWDATgIHQWD8D0aPz2lhtMh2PTSdyEArlDz9L1vgGOE%2b7MLl2gXMV8blL0cXnfeBS5VrZG5eJYTNvLdIVc94SkLFe0GfcT4m%2fZsDmcebnU2cb35I7qSdxnIBRZWihFtb8Ljg19TSQKNzuhMo9jZgIyvTRUu2aNgS0l5tFbMaoO9YPw%2bd2FaZ8I9orwl7nw%3d%3d";


    //设备id
    public static final String APP_ID="e18911500bd243e390fb32d86adf1076";

    //公司id
    public static final String COMPANY_ID="66b163de67ea43aba793ab6c5ad4b3a0";

    //快捷刷卡key
    public static final int QUICK_KEY=1;

    public static final String RETURN_URL="https://m.baidu.com";

    public static final String NOTIFY_URL="https://m.baidu.com";

    //七牛云AK
    public static final String QINIU_AK="Ob4ET-G7l6ysV3RWoUqIE7Ke56ZDPiQawK-14-zA";
    //七牛云sk
    public static final String QINIU_SK="unXoTd5CS8t18vGQ43RuTA0We-Qs9D7E_3llY_Zh";
    //七牛云空间名
    public static final String QINIU_BUCKET="bucket-one";
    //七牛云域名
    public static final String QINIU_URL="http://img.zhonghuatech.com/";
}
