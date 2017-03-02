package com.chris.money.bean;
/**
 * User: xiaoming
 * Date: 2017-02-20
 * Time: 15:56
 * 红包javabean
 */

import io.realm.RealmObject;

/**
 * Created by apple on 2017/2/20.
 */
public class Money extends RealmObject {
//    @PrimaryKey
//    public long id;//

    public long timeLong;//领取时间 格林毫秒时间
    public long timeYear;//领取年 2017
    public long timeMonth;//领取月 201702
    public long timeDay;//领取日 20170220

    public String name;//发送人 tom
    public float money;//金额 6.66
    public int    type;//0微信／1qq

}
