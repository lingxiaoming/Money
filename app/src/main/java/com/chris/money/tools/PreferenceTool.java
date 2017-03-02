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

    public void saveString(String key, String value) {
        SharedPreferences.Editor edit = preference.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public String getString(String key) {
        return preference.getString(key, "");
    }


    public void saveStringList(String key, List<String> values) {
        SharedPreferences.Editor edit = preference.edit();
        edit.putStringSet(key, new HashSet<>(values));
        edit.commit();
    }

    public List<String> getStringList(String key) {
        Set set = preference.getStringSet(key, new HashSet<String>());
        return new ArrayList<>(set);
    }

    public void saveInt(String key, int value) {
        SharedPreferences.Editor edit = preference.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public int getInt(String key) {
        return preference.getInt(key, 0);
    }

    public void saveBoolean(String key, boolean on){
        SharedPreferences.Editor edit = preference.edit();
        edit.putBoolean(key, on);
        edit.commit();
    }

    public boolean getBoolean(String key, boolean defaultBoolean){
        return preference.getBoolean(key, defaultBoolean);
    }

    public void saveFloat(String key, float f){
        SharedPreferences.Editor edit = preference.edit();
        edit.putFloat(key, f);
        edit.commit();
    }

    public float getFloat(String key){
        return preference.getFloat(key, 0);
    }
}
