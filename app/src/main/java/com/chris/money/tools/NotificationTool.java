package com.chris.money.tools;
/**
 * User: xiaoming
 * Date: 2017-02-22
 * Time: 14:22
 * 带按钮的通知
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.chris.money.MoneyApplication;
import com.chris.money.R;
import com.chris.money.constant.BroadCastConstant;
import com.chris.money.ctrl.SettingCtrl;
import com.chris.money.ui.StatiscalActivity;

import java.lang.reflect.Method;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by apple on 2017/2/22.
 */
public class NotificationTool implements BroadCastTool.OnReceiveBroadcast {
    public ButtonBroadcastReceiver bReceiver;
    private BroadcastReceiver receiver;//接收按钮状态改变广播
    public NotificationManager mNotificationManager;
    public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
    public final static String INTENT_BUTTONID_TAG = "ButtonId";
    public final static int BUTTON_ID_1 = 1;
    public final static int BUTTON_ID_2 = 2;
    public final static int BUTTON_ID_3 = 3;
    public final static int BUTTON_ID_4 = 4;

    private Context context;

    private RemoteViews mRemoteViews;
    private Notification notify;

    public NotificationTool(Context context) {
        mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        this.context = context;
        initButtonReceiver();
        initBroadcast();
    }

    private void initBroadcast() {
        receiver = BroadCastTool.registerBroadcastReceiver(context, new String[]{BroadCastConstant.ACTION_WECHAT_SWITCH, BroadCastConstant.ACTION_VOLUME,
                BroadCastConstant.ACTION_LOCK, BroadCastConstant.ACTION_NOTIFICATION}, this);
    }

    public void initButtonReceiver() {
        bReceiver = new ButtonBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BUTTON);
        context.registerReceiver(bReceiver, intentFilter);
    }

    /**
     * 带按钮的通知栏
     */
    public void showButtonNotify() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_notification);
//        mRemoteViews.setImageViewResource(R.id.tv_1, R.mipmap.ic_launcher);
//        mRemoteViews.setTextViewText(R.id.tv_1, "周杰伦");

        //点击的事件处理
        Intent buttonIntent = new Intent(ACTION_BUTTON);
        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_ID_1);
        //这里加了广播，所及INTENT的必须用getBroadcast方法
        PendingIntent intent_1 = PendingIntent.getBroadcast(context, 1, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.ll_option1, intent_1);

        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_ID_2);
        PendingIntent intent_2 = PendingIntent.getBroadcast(context, 2, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.ll_option2, intent_2);

        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_ID_3);
        PendingIntent intent_3 = PendingIntent.getBroadcast(context, 3, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.ll_option3, intent_3);

        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_ID_4);
        PendingIntent intent_4 = PendingIntent.getBroadcast(context, 4, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.ll_option4, intent_4);

        mBuilder.setContent(mRemoteViews)
                .setContentIntent(getDefalutIntent(Notification.FLAG_ONGOING_EVENT))
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                .setTicker("正在播放")
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setOngoing(true)
//                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher);
        notify = mBuilder.build();
        notify.flags = Notification.FLAG_ONGOING_EVENT;
        mNotificationManager.notify(1998, notify);


    }

    public PendingIntent getDefalutIntent(int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, new Intent(), flags);
        return pendingIntent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case BroadCastConstant.ACTION_WECHAT_SWITCH:
                if (MoneyApplication.wechatSwitch) {
                    mRemoteViews.setViewVisibility(R.id.view_cover_1, View.INVISIBLE);
                } else {
                    mRemoteViews.setViewVisibility(R.id.view_cover_1, View.VISIBLE);
                }
                mNotificationManager.notify(1998, notify);
                break;
            case BroadCastConstant.ACTION_VOLUME:
                if (MoneyApplication.otherVoice) {
                    mRemoteViews.setViewVisibility(R.id.view_cover_4, View.INVISIBLE);
                } else {
                    mRemoteViews.setViewVisibility(R.id.view_cover_4, View.VISIBLE);
                }
                mNotificationManager.notify(1998, notify);
                break;
            case BroadCastConstant.ACTION_LOCK:
                if (MoneyApplication.otherLock) {
                    mRemoteViews.setViewVisibility(R.id.view_cover_3, View.INVISIBLE);
                } else {
                    mRemoteViews.setViewVisibility(R.id.view_cover_3, View.VISIBLE);
                }
                mNotificationManager.notify(1998, notify);
                break;
            case BroadCastConstant.ACTION_NOTIFICATION:
                if(MoneyApplication.otherNotification){
                    showButtonNotify();
                }else {
                    cancelNotification();
                }

                break;
        }
    }

    public class ButtonBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_BUTTON)) {
                collapseStatusBar(context);
                int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
                switch (buttonId) {
                    case BUTTON_ID_1:
                        ToastTool.showWarn("BUTTON_ID_1");
                        SettingCtrl.changeWechatSwitch(!MoneyApplication.wechatSwitch);
                        break;
                    case BUTTON_ID_2:
                        ToastTool.showWarn("BUTTON_ID_2");
                        StatiscalActivity.goSingleTask(context);
                        break;
                    case BUTTON_ID_3:
                        ToastTool.showWarn("BUTTON_ID_3");
                        SettingCtrl.changeLockSwitch(!MoneyApplication.otherLock);
                        break;
                    case BUTTON_ID_4:
                        ToastTool.showWarn("BUTTON_ID_4");
                        SettingCtrl.changeVoiceSwitch(!MoneyApplication.otherVoice);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 关闭下拉通知栏
     * <p>
     * 需要添加权限：<uses-permission android:name="android.permission.EXPAND_STATUS_BAR" />
     *
     * @param context
     */
    public void collapseStatusBar(Context context) {
        try {
            Object statusBarManager = context.getSystemService("statusbar");
            Method collapse;
            if (Build.VERSION.SDK_INT <= 16) {
                collapse = statusBarManager.getClass().getMethod("collapse");
            } else {
                collapse = statusBarManager.getClass().getMethod("collapsePanels");
            }
            collapse.invoke(statusBarManager);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public void destoryReceiver() {
        BroadCastTool.unRegisterBroadcastReceiver(context, receiver);
        context.unregisterReceiver(bReceiver);
    }

    public void cancelNotification(){
        mNotificationManager.cancelAll();
        destoryReceiver();
    }
}
