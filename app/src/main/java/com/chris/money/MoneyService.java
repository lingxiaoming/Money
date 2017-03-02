package com.chris.money;
/**
 * User: xiaoming
 * Date: 2017-02-22
 * Time: 23:35
 * 辅助服务类，抢红包就在这里了
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;

import com.chris.money.base.BaseAccessibilityService;
import com.chris.money.constant.UIConstants;
import com.chris.money.ctrl.MoneyCtrl;
import com.chris.money.tools.AccessibilitySettingTool;
import com.chris.money.tools.LogTool;
import com.chris.money.tools.ToastTool;
import com.chris.money.ui.MainActivity;

/**
 * Created by apple on 2017/2/22.
 */
public class MoneyService extends BaseAccessibilityService {
    private static final String TAG = "MoneyService";
    private MoneyCtrl mMoneyCtrl;//红包功能分支
    public static String mTopClassname;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if(!MoneyApplication.mSwitch) return;//总开关
//        if(!MoneyApplication.getInstance().hasRegiste()) return;//没有注册直接返回

        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            mTopClassname = accessibilityEvent.getClassName().toString();
            LogTool.d(TAG, "TopClass:"+mTopClassname);
        }

        if(MoneyApplication.wechatSwitch && TextUtils.equals(accessibilityEvent.getPackageName().toString(), UIConstants.PACKAGE_WEIXIN)){
            mMoneyCtrl.dispathRedpackage(this, accessibilityEvent);
        }
    }

    @Override
    public void onInterrupt() {
        //服务中断，如授权关闭或者将服务杀死
        ToastTool.showWarn("快点服务不小心被关闭了，请重新启动哦～");
        AccessibilitySettingTool.gotoSwitchService(this);
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        //连接服务后,一般是在授权成功后会接收到
        mMoneyCtrl = new MoneyCtrl();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO 设置通知使应用长期处于前台
        initNofitication();
        notificationManager.notify(NOTIFICATION_ID, notification);
        startForeground(NOTIFICATION_ID, notification);

        return START_STICKY;
    }

    public final static int NOTIFICATION_ID = "MainActivity".hashCode();
    private NotificationManager notificationManager;
    private Notification notification;

    public void initNofitication(){
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // params
        int smallIconId = R.mipmap.ic_launcher;
        Bitmap largeIcon = ((BitmapDrawable) getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap();
        String info = "快点抢红包打开啦";

        // action when clicked
        Intent intent = new Intent(this, MainActivity.class);

        builder.setLargeIcon(largeIcon)
                .setSmallIcon(smallIconId)
                .setContentTitle("快点")
                .setContentText(info)
                .setTicker(info)
                .setAutoCancel(false)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(PendingIntent.getActivity(this, 0, intent, 0));

        notification = builder.getNotification();
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        Intent intent = new Intent(UIConstants.ACTION_SERVICE_CLOSE);
        sendBroadcast(intent);
        if(notificationManager != null) {
            notificationManager.cancel(NOTIFICATION_ID);
        }
        super.onDestroy();
    }
}
