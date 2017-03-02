package com.chris.money.constant;
/**
 * User: xiaoming
 * Date: 2017-02-22
 * Time: 22:07
 * 自定义广播action常量值
 */

/**
 * Created by apple on 2017/2/22.
 */
public class BroadCastConstant {
    //总开关
    public static final String ACTION_SWITCH = "ACTION_SWITCH";//总开关

    //其他设置开关
    public static final String ACTION_VOLUME = "ACTION_VOLUME";//抢到红包声音开关
    public static final String ACTION_LOCK   = "ACTION_LOCK";  //锁屏下抢红包开关
    public static final String ACTION_NOTIFICATION = "ACTION_NOTIFICATION";//通知栏显示开关
    //微信设置开关
    public static final String ACTION_WECHAT_SWITCH = "ACTION_WECHAT_SWITCH";//抢微信红包开关
    public static final String ACTION_WECHAT_DELAY = "ACTION_WECHAT_DELAY";//微信延时抢，时间改变
    public static final String ACTION_WECHAT_IGNORE_SWITCH = "ACTION_WECHAT_IGNORE_SWITCH";//微信忽略词开关
    public static final String ACTION_WECHAT_IGNORE_CHANGE = "ACTION_WECHAT_IGNORE_CHANGE";//微信忽略词增／减
    public static final String ACTION_WECHAT_THANKS_SWITCH = "ACTION_WECHAT_THANKS_SWITCH";//微信感谢开关
    public static final String ACTION_WECHAT_THANKS_CHANGE = "ACTION_WECHAT_THANKS_CHANGE";//微信感谢增／减
    //
}
