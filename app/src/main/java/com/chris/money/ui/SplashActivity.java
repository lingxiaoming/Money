package com.chris.money.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;

import androidx.appcompat.app.AppCompatActivity;

import com.chris.money.R;
import com.chris.money.tools.AccessibilitySettingTool;
import com.chris.money.tools.LogTool;
import com.chris.money.tools.ToastTool;

public class SplashActivity extends AppCompatActivity implements AccessibilityManager.AccessibilityStateChangeListener {

    private static final String TAG = "SplashActivity";
    private AccessibilityManager mAccessibilityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViews();
        prepareAccessibility();
    }

    private void initViews() {

    }


    public void prepareAccessibility(){
        mAccessibilityManager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
        mAccessibilityManager.addAccessibilityStateChangeListener(this);

        if(AccessibilitySettingTool.isAccessibilitySettingsOn()){
            goMain();
        }else {
            AccessibilitySettingTool.gotoSwitchService(this);
        }
    }

    public void goMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onAccessibilityStateChanged(boolean enabled) {
        LogTool.d(TAG, "onAccessibilityStateChanged " + enabled);
        if (enabled) {
            goMain();
        } else {
            ToastTool.showError("点击任意地方进入设置页面，打开快点辅助");
        }
    }

    public void onClick(View view){
        AccessibilitySettingTool.gotoSwitchService(this);
    }
}
