package com.chris.money.tools;
/**
 * User: xiaoming
 * Date: 2016-05-16
 * Time: 00:30
 * 描述一下这个类吧
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2015/6/9.
 */
public class MD5Tool {

    private static final String TAG = "MD5Tool";


    /**
     * Md5 32位 or 16位 加密
     *
     * @param plainText
     *
     * @return 32位加密
     */
    public static String Md5(String plainText) {
        String encodeStr = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            encodeStr = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//        CLog.e(TAG, "Md5 32 encodeStr: " + encodeStr);//32位的加密
//        CLog.e(TAG,"Md5 16 encodeStr: " + encodeStr.substring(8,24));//16位的加密
        return encodeStr;
    }

}

