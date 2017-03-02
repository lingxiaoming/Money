package com.chris.money.tools;

import java.text.DecimalFormat;

/**
 * 工具类
 * Created by hanj on 14-12-30.
 */
public class UtilityTool {
    /**
     * 格式化浮点数据，保留1位小数
     */
    public static String formatFloat(double value) {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(value);
    }

    public static int getScale(float value) {
        if (value >= 1 && value < 10) {
            return 0;
        }

        if (value >= 10) {
            return 1 + getScale(value / 10);
        } else {
            return getScale(value * 10) - 1;
        }
    }
}
