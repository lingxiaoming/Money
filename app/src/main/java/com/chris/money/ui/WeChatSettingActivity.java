package com.chris.money.ui;
/**
 * User: xiaoming
 * Date: 2017-02-19
 * Time: 10:46
 * 微信红包设置页面
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.chris.money.MoneyApplication;
import com.chris.money.R;
import com.chris.money.constant.BroadCastConstant;
import com.chris.money.ctrl.SettingCtrl;
import com.chris.money.tools.BroadCastTool;
import com.chris.money.views.ListViewPreference;

/**
 * Created by apple on 2017/2/19.
 */
public class WeChatSettingActivity extends AppCompatPreferenceActivity implements Preference.OnPreferenceChangeListener, BroadCastTool.OnReceiveBroadcast {
    private static ListViewPreference mListViewPreferenceIgnore;//过滤词列表
    private static ListViewPreference mListViewPreferenceAnswer;//回复语列表

    private BroadcastReceiver receiver;

    private SwitchPreference mWechatSwitch;
    private SwitchPreference mWechatIgnores;
    private SwitchPreference mWechatAnswers;

    public static void go(Context context) {
        Intent intent = new Intent(context, WeChatSettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_wechat);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("微信红包设置");


        bindPreferenceSummaryToValue(findPreference("wechat_edit_time"));
        bindPreferenceSummaryToValue(findPreference("wechat_edit_ignores"));
        bindPreferenceSummaryToValue(findPreference("wechat_edit_answers"));

        initViews();
        initBroadcast();
    }

    private void initBroadcast() {
        receiver = BroadCastTool.registerBroadcastReceiver(this, new String[]{BroadCastConstant.ACTION_WECHAT_SWITCH}, this);
    }

    private void initViews() {
        mListViewPreferenceIgnore = (ListViewPreference) findPreference("wechat_list_ignores");
        mListViewPreferenceAnswer = (ListViewPreference) findPreference("wechat_list_answers");

        mWechatSwitch = (SwitchPreference) findPreference("wechat_switch");
        mWechatIgnores = (SwitchPreference) findPreference("wechat_switch_ignores");
        mWechatAnswers = (SwitchPreference) findPreference("wechat_switch_answers");
        mWechatSwitch.setChecked(MoneyApplication.wechatSwitch);
        mWechatIgnores.setChecked(MoneyApplication.wechatIgnore);
        mWechatAnswers.setChecked(MoneyApplication.wechatAnswer);
        mWechatSwitch.setOnPreferenceChangeListener(this);
        mWechatIgnores.setOnPreferenceChangeListener(this);
        mWechatAnswers.setOnPreferenceChangeListener(this);
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


    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            String preferenceKey = preference.getKey();

            preference.setSummary(stringValue);

            switch (preferenceKey) {
                case "wechat_edit_time":
                    float time = Float.parseFloat(stringValue);
                    SettingCtrl.changeWechatDelay(time);
                    break;
                case "wechat_edit_ignores":
                    if (mListViewPreferenceIgnore != null) {
                        mListViewPreferenceIgnore.addItem(stringValue);
                    }
                    SettingCtrl.changeWechatIgnoreList(true, stringValue);
                    break;
                case "wechat_edit_answers":
                    if (mListViewPreferenceAnswer != null) {
                        mListViewPreferenceAnswer.addItem(stringValue);
                    }
                    SettingCtrl.changeWechatThanksList(true, stringValue);
                    break;
            }

            return true;
        }
    };

    @Override
    public boolean onPreferenceChange(Preference preference, Object object) {
        String key = preference.getKey();
        boolean open = Boolean.parseBoolean(object.toString());
        switch (key) {
            case "wechat_switch":
                SettingCtrl.changeWechatSwitch(open);
                break;
            case "wechat_switch_ignores":
                SettingCtrl.changeWechatIgnore(open);
                break;
            case "wechat_switch_answers":
                SettingCtrl.changeWechatAnwser(open);
                break;
        }
        return true;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case BroadCastConstant.ACTION_WECHAT_SWITCH:
                mWechatSwitch.setChecked(MoneyApplication.wechatSwitch);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BroadCastTool.unRegisterBroadcastReceiver(this, receiver);
    }
}
