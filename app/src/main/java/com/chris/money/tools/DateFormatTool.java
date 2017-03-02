package com.chris.money.tools;
/**
 * User: xiaoming
 * Date: 2017-02-20
 * Time: 22:31
 * 描述一下这个类吧
 */

import java.util.Date;

/**
 * Created by apple on 2017/2/20.
 */
public class DateFormatTool {
    public static int getYear(long date){//2017
        Date d=new Date(date);
        return d.getYear()+1900;
    }

    public static int getMonth(long date){//201702
        Date d=new Date(date);
        return d.getMonth()+1+getYear(date)*100;
    }

    public static int getDay(long date){//20170220
        Date d=new Date(date);
        return d.getDate()+getMonth(date)*100;
    }
}
