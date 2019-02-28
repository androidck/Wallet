package com.minmai.wallet.common.uitl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 字符格式化
 */
public class TextUtil {

    /**
     * 保留两位小数
     * @param value
     * @return
     */
    public static String format2(double value) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(value);
    }

    /**
     * 格式化金额
     * @param str
     * @return
     */
    public static String formatMoney(BigDecimal str){
        DecimalFormat df2 =new DecimalFormat("#.00");
        String str2 =df2.format(str);
        return str2;
    }

    /**
     * 街区银行卡后四位
     * @param str
     * @return
     */
    public static String subCardNo(String str){
        String content=str.substring(str.length()-4);
        return content;
    }
}
