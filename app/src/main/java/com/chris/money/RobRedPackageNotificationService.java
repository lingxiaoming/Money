package com.chris.money;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

public class RobRedPackageNotificationService extends NotificationListenerService {
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public void onListenerConnected() {
        // 已经连接上了
    }

    @Override
    public void onNotificationPosted(final StatusBarNotification sbn) {
        //接收通知栏事件
        sbn.getId(); // 返回通知对应的id
        sbn.getNotification(); // 返回通知对象
        sbn.getPackageName(); // 返回通知对应的包名
        sbn.getNotification(); // 返回通知对象
        String title = sbn.getNotification().tickerText.toString();// 通知栏的消息内容
        // 发送广播，将消息传出去,或者 直接设置静态函数传递消息
        // 在【辅助功能/无障碍】中处理
        MoneyService.notificationEvent(title,sbn);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}