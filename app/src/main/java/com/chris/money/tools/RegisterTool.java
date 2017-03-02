package com.chris.money.tools;

import android.provider.Settings;

import com.chris.money.MoneyApplication;

/**
 * 这里描述下用途吧
 * Created by lingxiaoming on 2016/6/21 0021.
 */
public class RegisterTool {
    public static String getAndroidId(){
        return Settings.Secure.getString(MoneyApplication.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getMyCode(){
        String androidIdToMd5String = MD5Tool.Md5(getAndroidId() + "ling4766897");

        char[] chars = androidIdToMd5String.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(chars[4]);
        stringBuffer.append(chars[7]);
        stringBuffer.append(chars[1]);
        stringBuffer.append(chars[6]);
        stringBuffer.append(chars[5]);
        return stringBuffer.toString();
    }
}
