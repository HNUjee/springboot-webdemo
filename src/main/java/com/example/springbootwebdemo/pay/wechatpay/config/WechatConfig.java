package com.example.springbootwebdemo.pay.wechatpay.config;

public class WechatConfig {

    /**
     * 微信开发平台应用ID
     */
    public static final String APP_ID = "wx86329aaccbb8f2f2";
    /**
     * 应用对应的凭证
     */
    public static final String APP_SECRET = "85ea6d0976184346b680e15bdf72c107";
    /**
     * 应用对应的密钥
     */
    public static final String APP_KEY = "1nRXE6W36dPb9mmFHwi7iKIAseCzPGN5";
    /**
     * 微信支付商户号
     */
    public static final String MCH_ID = "1512348711";
    /**
     * 商品描述
     */
    public static final String BODY = "";
    /**
     * 商户号对应的密钥
     */
    public static final String PARTNER_key = "";

    /**
     * 商户id
     */
    public static final String PARTNER_ID = "";
    /**
     * 常量固定值
     */
    public static final String GRANT_TYPE = "client_credential";
    /**
     * 获取预支付id的接口url
     */
    public static String GATEURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 微信服务器回调通知url
     */
    public static String NOTIFY_URL = "https://xxx.com/wechatPay/notifyWeiXinPay";

    /**
     * 微信退款服务器回调通知url
     */
    public static String NOTIFY_REFUND_URL = "https://xxx.com/wechatPay/notifyWeiXinRefund";
}
