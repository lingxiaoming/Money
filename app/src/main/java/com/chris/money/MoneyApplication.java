package com.chris.money;
/**
 * User: xiaoming
 * Date: 2017-02-17
 * Time: 16:44
 * 描述一下这个类吧
 */

import android.app.Application;
import android.text.TextUtils;

import com.chris.money.constant.PreferenceConstant;
import com.chris.money.tools.PreferenceTool;
import com.chris.money.tools.RegisterTool;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by apple on 2017/2/17.
 */
public class MoneyApplication extends Application {
    private static MoneyApplication mInstance;
    public static MoneyApplication getInstance(){
        return mInstance;
    }

    public static boolean wechatSwitch = false;//微信抢开关
    public static float wechatDelay = 0;//微信延时秒数
    public static boolean wechatIgnore = false;//微信忽略词开关
    public static boolean wechatAnswer = false;//微信回复感谢语语开关

    public static List<String> wechatIgnoreList;//微信忽略词列表
    public static List<String> wechatAnswerList;//微信感谢语列表


    public static boolean otherVoice = false;//抢到红包声音提示开关
    public static boolean otherLock = false;//锁屏下抢红包开关
    public static boolean otherNotification = false;//通知栏快捷入口开关

    public static String registerCode;//需要的注册码
    public static String userRegisterCode;//用户输入的注册码
    public static String androidID;

    public static boolean mSwitch = false;//抢红包总开关

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);

        initLocalDatas();
    }

    private void initLocalDatas() {
        wechatSwitch = PreferenceTool.getInstance().getBoolean(PreferenceConstant.KEY_WECHAT_SWITCH, false);
        wechatDelay = PreferenceTool.getInstance().getFloat(PreferenceConstant.KEY_WECHAT_DELAY);
        wechatIgnore = PreferenceTool.getInstance().getBoolean(PreferenceConstant.KEY_WECHAT_IGNORE, true);
        wechatAnswer = PreferenceTool.getInstance().getBoolean(PreferenceConstant.KEY_WECHAT_ANSWER, true);

        wechatIgnoreList = PreferenceTool.getInstance().getStringList(PreferenceConstant.KEY_WECHAT_IGNORE_LIST);
        wechatAnswerList = PreferenceTool.getInstance().getStringList(PreferenceConstant.KEY_WECHAT_ANSWER_LIST);

        otherVoice = PreferenceTool.getInstance().getBoolean(PreferenceConstant.KEY_OTHER_VOICE, false);
        otherLock = PreferenceTool.getInstance().getBoolean(PreferenceConstant.KEY_OTHER_LOCK, true);
        otherNotification = PreferenceTool.getInstance().getBoolean(PreferenceConstant.KEY_OTHER_NOTIFICATION, true);

        registerCode = RegisterTool.getMyCode();
        userRegisterCode = PreferenceTool.getInstance().getString(PreferenceConstant.KEY_REGISTER_CODE);
        androidID = RegisterTool.getAndroidId();

    }

    public boolean hasRegiste(){
        return TextUtils.equals(registerCode, userRegisterCode);
    }

    public void updateCode(){
        registerCode = PreferenceTool.getInstance().getString(PreferenceConstant.KEY_REGISTER_CODE);
    }

}
