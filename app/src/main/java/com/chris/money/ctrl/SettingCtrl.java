package com.chris.money.ctrl;
/**
 * User: xiaoming
 * Date: 2017-02-23
 * Time: 10:40
 * 设置修改控制类，这里做统一的修改全局变量设置，广播发送
 */

import com.chris.money.MoneyApplication;
import com.chris.money.constant.BroadCastConstant;
import com.chris.money.constant.PreferenceConstant;
import com.chris.money.tools.BroadCastTool;
import com.chris.money.tools.NotificationTool;
import com.chris.money.tools.PreferenceTool;

import java.util.List;

/**
 * Created by apple on 2017/2/23.
 */
public class SettingCtrl {

    /**
     * 抢红包总开关改变
     *
     * @param on
     */
    public static void changeSwitch(boolean on) {
        PreferenceTool.getInstance().saveBoolean(PreferenceConstant.KEY_SWITCH, on);
        MoneyApplication.mSwitch = on;
        BroadCastTool.sendBroadcast(MoneyApplication.getInstance(), BroadCastConstant.ACTION_SWITCH);
    }

    /**
     * 微信抢红包开关改变
     *
     * @param on
     */
    public static void changeWechatSwitch(boolean on) {
        PreferenceTool.getInstance().saveBoolean(PreferenceConstant.KEY_WECHAT_SWITCH, on);
        MoneyApplication.wechatSwitch = on;
        BroadCastTool.sendBroadcast(MoneyApplication.getInstance(), BroadCastConstant.ACTION_WECHAT_SWITCH);
    }

    /**
     * 微信延迟抢时间改变
     *
     * @param f
     */
    public static void changeWechatDelay(float f) {
        PreferenceTool.getInstance().saveFloat(PreferenceConstant.KEY_WECHAT_DELAY, f);
        MoneyApplication.wechatDelay = f;
        BroadCastTool.sendBroadcast(MoneyApplication.getInstance(), BroadCastConstant.ACTION_WECHAT_DELAY);
    }

    /**
     * 微信忽略词开关改变
     *
     * @param on
     */
    public static void changeWechatIgnore(boolean on) {
        PreferenceTool.getInstance().saveBoolean(PreferenceConstant.KEY_WECHAT_IGNORE, on);
        MoneyApplication.wechatIgnore = on;
        BroadCastTool.sendBroadcast(MoneyApplication.getInstance(), BroadCastConstant.ACTION_WECHAT_IGNORE_SWITCH);
    }

    /**
     * 微信忽略词增／删
     *
     * @param add  true增加，false删除
     * @param word 增加／删除的单词
     */
    public static void changeWechatIgnoreList(boolean add, String word) {
        List<String> list = PreferenceTool.getInstance().getStringList(PreferenceConstant.KEY_WECHAT_IGNORE_LIST);
        if (add) {
            if (list != null && !list.contains(word)) {
                list.add(word);
            }
        } else {
            if (list != null && list.contains(word)) {
                list.remove(word);
            }
        }
        MoneyApplication.wechatIgnoreList = list;
        BroadCastTool.sendBroadcast(MoneyApplication.getInstance(), BroadCastConstant.ACTION_WECHAT_IGNORE_CHANGE);
    }

    /**
     * 微信回复感谢词开关改变
     *
     * @param on
     */
    public static void changeWechatAnwser(boolean on) {
        PreferenceTool.getInstance().saveBoolean(PreferenceConstant.KEY_WECHAT_ANSWER, on);
        MoneyApplication.wechatAnswer = on;
        BroadCastTool.sendBroadcast(MoneyApplication.getInstance(), BroadCastConstant.ACTION_WECHAT_THANKS_SWITCH);
    }

    /**
     * 微信感谢词增／删
     *
     * @param add  true增加，false删除
     * @param word 增加／删除的单词
     */
    public static void changeWechatThanksList(boolean add, String word) {
        List<String> list = PreferenceTool.getInstance().getStringList(PreferenceConstant.KEY_WECHAT_ANSWER_LIST);
        if (add) {
            if (list != null && !list.contains(word)) {
                list.add(word);
            }
        } else {
            if (list != null && list.contains(word)) {
                list.remove(word);
            }
        }
        MoneyApplication.wechatAnswerList = list;
        BroadCastTool.sendBroadcast(MoneyApplication.getInstance(), BroadCastConstant.ACTION_WECHAT_THANKS_CHANGE);
    }


    /**
     * 抢到红包声音开关改变
     *
     * @param on
     */
    public static void changeVoiceSwitch(boolean on) {
        PreferenceTool.getInstance().saveBoolean(PreferenceConstant.KEY_OTHER_VOICE, on);
        MoneyApplication.otherVoice = on;
        BroadCastTool.sendBroadcast(MoneyApplication.getInstance(), BroadCastConstant.ACTION_VOLUME);
    }

    /**
     * 锁屏下抢红包开关改变
     *
     * @param on
     */
    public static void changeLockSwitch(boolean on) {
        PreferenceTool.getInstance().saveBoolean(PreferenceConstant.KEY_OTHER_LOCK, on);
        MoneyApplication.otherLock = on;
        BroadCastTool.sendBroadcast(MoneyApplication.getInstance(), BroadCastConstant.ACTION_LOCK);
    }

    /**
     * 通知栏快捷开关改变
     *
     * @param on
     */
    public static void changeNotificationSwitch(boolean on) {
        PreferenceTool.getInstance().saveBoolean(PreferenceConstant.KEY_OTHER_NOTIFICATION, on);
        MoneyApplication.otherNotification = on;
        BroadCastTool.sendBroadcast(MoneyApplication.getInstance(), BroadCastConstant.ACTION_NOTIFICATION);

        if (on) {
            NotificationTool notificationTool = new NotificationTool(MoneyApplication.getInstance());
            notificationTool.showButtonNotify();
        }
    }
}
