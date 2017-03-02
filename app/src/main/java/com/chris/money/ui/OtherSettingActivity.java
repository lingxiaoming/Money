package com.chris.money.ui;
/**
 * User: xiaoming
 * Date: 2017-02-19
 * Time: 10:46
 * 其他设置页面
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.chris.money.MoneyApplication;
import com.chris.money.R;
import com.chris.money.constant.BroadCastConstant;
import com.chris.money.ctrl.SettingCtrl;
import com.chris.money.tools.BroadCastTool;
import com.chris.money.tools.ToastTool;

/**
 * Created by apple on 2017/2/19.
 */
public class OtherSettingActivity extends AppCompatPreferenceActivity implements Preference.OnPreferenceChangeListener, BroadCastTool.OnReceiveBroadcast {

    private BroadcastReceiver receiver;
    private SwitchPreference mSoundSwitch;
    private SwitchPreference mLockSwitch;
    private SwitchPreference mNotificationSwitch;


    public static void go(Context context) {
        Intent intent = new Intent(context, OtherSettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_other);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("其他设置");

        initViews();
        initBroadcast();
    }

    private void initBroadcast() {
        receiver = BroadCastTool.registerBroadcastReceiver(this, new String[]{BroadCastConstant.ACTION_VOLUME,
                BroadCastConstant.ACTION_LOCK, BroadCastConstant.ACTION_NOTIFICATION}, this);
    }

    private void initViews() {
        mSoundSwitch = (SwitchPreference) findPreference("money_sound_switch");
        mLockSwitch = (SwitchPreference) findPreference("money_lock_switch");
        mNotificationSwitch = (SwitchPreference) findPreference("money_notification_switch");

        mSoundSwitch.setChecked(MoneyApplication.otherVoice);
        mLockSwitch.setChecked(MoneyApplication.otherLock);
        mNotificationSwitch.setChecked(MoneyApplication.otherNotification);

        mSoundSwitch.setOnPreferenceChangeListener(this);
        mLockSwitch.setOnPreferenceChangeListener(this);
        mNotificationSwitch.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object object) {
        String key = preference.getKey();
        ToastTool.showWarn(key + " " + object);
        boolean isOpen = (Boolean) object;
        switch (key) {
            case "money_sound_switch":
                SettingCtrl.changeVoiceSwitch(isOpen);
                break;
            case "money_lock_switch":
                SettingCtrl.changeLockSwitch(isOpen);
                break;
            case "money_notification_switch":
                SettingCtrl.changeNotificationSwitch(isOpen);
                break;
        }
        return true;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case BroadCastConstant.ACTION_VOLUME:
                mSoundSwitch.setChecked(MoneyApplication.otherVoice);
                break;
            case BroadCastConstant.ACTION_LOCK:
                mLockSwitch.setChecked(MoneyApplication.otherLock);
                break;
            case BroadCastConstant.ACTION_NOTIFICATION:
                mNotificationSwitch.setChecked(MoneyApplication.otherNotification);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BroadCastTool.unRegisterBroadcastReceiver(this, receiver);
    }
}
