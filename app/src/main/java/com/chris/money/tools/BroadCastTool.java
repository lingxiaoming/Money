package com.chris.money.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import java.io.Serializable;

/**
 * 注册广播，发送广播，注销广播
 */
public class BroadCastTool {
    private static LocalBroadcastManager mLocalBroadcastManager;

    private static void getLocalBroadcastManager(Context context) {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    public interface OnReceiveBroadcast {
        public void onReceive(Context context, Intent intent);
    }

    private static class CustomBroadcastReceiver extends BroadcastReceiver {
        private OnReceiveBroadcast onReceiveBroadcast;

        public CustomBroadcastReceiver(OnReceiveBroadcast onReceiveBroadcast) {
            this.onReceiveBroadcast = onReceiveBroadcast;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (onReceiveBroadcast != null) {
                onReceiveBroadcast.onReceive(context, intent);
            }
        }
    }

    public static BroadcastReceiver registerBroadcastReceiver(Context context,
                                                              String[] filters, OnReceiveBroadcast onReceiveBroadcast) {
        BroadcastReceiver broadcastReceiver = new CustomBroadcastReceiver(
                onReceiveBroadcast);
        IntentFilter filter = new IntentFilter();
        for (String filterStr : filters) {
            filter.addAction(filterStr);
        }
        if (mLocalBroadcastManager == null) {
            getLocalBroadcastManager(context);
        }
        mLocalBroadcastManager.registerReceiver(broadcastReceiver, filter);
        return broadcastReceiver;
    }

    public static void sendBroadcast(Context context, String filter) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(filter);
        if (mLocalBroadcastManager == null) {
            getLocalBroadcastManager(context.getApplicationContext());
        }
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    public static void sendBroadcast(Context context, String filter, Bundle bundle) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(filter);
        intent.putExtras(bundle);
        if(mLocalBroadcastManager == null){
            getLocalBroadcastManager(context.getApplicationContext());
        }
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    public static void sendBroadcast(Context context, String filter,
                                     String bundleName, Bundle bundle) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(filter);
        intent.putExtra(bundleName, bundle);
        if(mLocalBroadcastManager == null){
            getLocalBroadcastManager(context.getApplicationContext());
        }
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    public static void sendBroadcast(Context context, String filter, String name, Serializable serializable) {
        Intent intent = new Intent();
        intent.putExtra(name, serializable);
        intent.setAction(filter);
        if(mLocalBroadcastManager == null){
            getLocalBroadcastManager(context.getApplicationContext());
        }
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    public static void sendBroadcast(Context context, String filter, String name, String value) {
        Intent intent = new Intent();
        intent.putExtra(name, value);
        intent.setAction(filter);
        if(mLocalBroadcastManager == null){
            getLocalBroadcastManager(context.getApplicationContext());
        }
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    public static void sendBroadcast(Context context, String filter, String name, long value) {
        Intent intent = new Intent();
        intent.putExtra(name, value);
        intent.setAction(filter);
        if(mLocalBroadcastManager == null){
            getLocalBroadcastManager(context.getApplicationContext());
        }
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    public static void sendBroadcast(Context context, String filter, String name, int value) {
        Intent intent = new Intent();
        intent.putExtra(name, value);
        intent.setAction(filter);
        if(mLocalBroadcastManager == null){
            getLocalBroadcastManager(context.getApplicationContext());
        }
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    public static void sendBroadcast(Context context, String filter, String name, String value, String name1, String value1) {
        Intent intent = new Intent();
        intent.putExtra(name, value);
        intent.putExtra(name1, value1);
        intent.setAction(filter);
        if(mLocalBroadcastManager == null){
            getLocalBroadcastManager(context.getApplicationContext());
        }
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    public static void sendBroadcast(Context context, Intent intent){
        if(mLocalBroadcastManager == null){
            getLocalBroadcastManager(context.getApplicationContext());
        }
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    public static void unRegisterBroadcastReceiver(Context context,
                                                   BroadcastReceiver broadcastReceiver) {
        if (context != null && broadcastReceiver != null) {
            if(mLocalBroadcastManager == null){
                getLocalBroadcastManager(context.getApplicationContext());
            }
            mLocalBroadcastManager.unregisterReceiver(broadcastReceiver);
        }
    }

}
