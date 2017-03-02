package com.chris.money.tools;
/**
 * User: xiaoming
 * Date: 2017-02-27
 * Time: 21:43
 * 辅助类工具，检查辅助类是否打开、进入辅助类开关设置页面
 */

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;

import com.chris.money.MoneyApplication;

/**
 * Created by apple on 2017/2/27.
 */
public class AccessibilitySettingTool {
    private static final String TAG = "AccessibilitySettingTool";

    // To check if service is enabled
    public static boolean isAccessibilitySettingsOn() {
        int accessibilityEnabled = 0;
        final String service = "com.chris.money/com.chris.money.MoneyService";
        boolean accessibilityFound = false;
        try {
            accessibilityEnabled = Settings.Secure.getInt(MoneyApplication.getInstance().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            LogTool.e(TAG, "Error finding setting, default accessibility to not found: " + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            LogTool.d(TAG, "***ACCESSIBILIY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(MoneyApplication.getInstance().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                TextUtils.SimpleStringSplitter splitter = mStringColonSplitter;
                splitter.setString(settingValue);
                while (splitter.hasNext()) {
                    String accessabilityService = splitter.next();

                    LogTool.d(TAG, "-------------- > accessabilityService :: " + accessabilityService);
                    if (accessabilityService.equalsIgnoreCase(service)) {
                        LogTool.d(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            LogTool.d(TAG, "***ACCESSIBILIY IS DISABLED***");
        }

        return accessibilityFound;
    }

    public static void gotoSwitchService(Context context) {
        if (AccessibilitySettingTool.isAccessibilitySettingsOn()) {
            Intent mAccessbilitySettings = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            context.startActivity(mAccessbilitySettings);
            ToastTool.showWarn("在辅助功能-服务中\n关闭\"快点抢红包\"");
        } else {
            Intent mAccessbilitySettings = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            context.startActivity(mAccessbilitySettings);
            ToastTool.showWarn("在辅助功能-服务中\n开启\"快点抢红包\"");
            return;
        }
    }
}
