package com.minmai.wallet.common.uitl;

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
}
