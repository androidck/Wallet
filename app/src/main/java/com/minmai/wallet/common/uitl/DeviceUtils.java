package com.minmai.wallet.common.uitl;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.Locale;

public class DeviceUtils {
    /**
     * 获取终端型号
     */
    public static String getTem_Model() {
        String tem_no = android.os.Build.MODEL;
        return tem_no;
    }

    /**
     * 获取终端品牌
     */
    public static String getTem_Release() {
        String tem_title = android.os.Build.VERSION.RELEASE;
        return tem_title;
    }



    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }




}
