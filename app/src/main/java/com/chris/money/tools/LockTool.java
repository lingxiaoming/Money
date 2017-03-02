package com.chris.money.tools;
/**
 * User: xiaoming
 * Date: 2017-02-22
 * Time: 11:14
 * 解锁工具类
 */

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.os.PowerManager;

/**
 * Created by apple on 2017/2/22.
 */
public class LockTool {
    public static void wakeUpAndUnlock(Activity activity) {
        if (shouldAskPermission()) {
            String[] perms = {"android.permission.DISABLE_KEYGUARD"};
            int permsRequestCode = 200;
            activity.requestPermissions(perms, permsRequestCode);
        } else {


            KeyguardManager km = (KeyguardManager) activity.getSystemService(Context.KEYGUARD_SERVICE);
            KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");

            if (km.inKeyguardRestrictedInputMode()) {
                KeyguardManager.KeyguardLock keyguard = km.newKeyguardLock(activity.getLocalClassName());
                keyguard.disableKeyguard();
            }
            //解锁
//        kl.disableKeyguard();
            //获取电源管理器对象
            PowerManager pm = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
            //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
            //点亮屏幕
            wl.acquire();

            //初始化键盘锁，可以锁定或解开键盘锁
            KeyguardManager.KeyguardLock mKeyguardLock = km.newKeyguardLock("");
            //禁用显示键盘锁定
            mKeyguardLock.disableKeyguard();
            //释放
//        wl.release();

        }
    }

    public static boolean shouldAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }
}
