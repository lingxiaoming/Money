package com.chris.money.tools;
/**
 * User: xiaoming
 * Date: 2017-02-19
 * Time: 17:07
 * 描述一下这个类吧
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.chris.money.MoneyApplication;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by apple on 2017/2/19.
 */
public class PreferenceTool {

    private static PreferenceTool mPrefsUtils;
    private SharedPreferences preference;
    private final static String MONEY_PREFS = "money_prefs";


    public static PreferenceTool getInstance() {
        if (null == mPrefsUtils) {
            mPrefsUtils = new PreferenceTool();
        }
        return mPrefsUtils;
    }

    private PreferenceTool() {
        preference = MoneyApplication.getInstance().getSharedPreferences(MONEY_PREFS, Context.MODE_PRIVATE);
    }

    public void saveStringByKey(String key, String value) {
        SharedPreferences.Editor edit = preference.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public String getStringByKey(String key) {
        return preference.getString(key, "");
    }


    public void saveStringListByKey(String key, List<String> values) {
        SharedPreferences.Editor edit = preference.edit();
        edit.putStringSet(key, new HashSet<>(values));
        edit.commit();
    }

    public List<String> getStringListByKey(String key) {
        Set set = preference.getStringSet(key, new HashSet<String>());
        return new ArrayList<>(set);
    }

    public void saveIntByKey(String key, int value) {
        SharedPreferences.Editor edit = preference.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public int getIntByKey(String key) {
        return preference.getInt(key, -888);//这个默认是故意这样写的，避免被用户串改prefrence数据
    }
}
