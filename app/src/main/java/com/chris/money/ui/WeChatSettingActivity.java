package com.chris.money.ui;
/**
 * User: xiaoming
 * Date: 2017-02-19
 * Time: 10:46
 * 微信红包设置页面
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.chris.money.R;
import com.chris.money.views.ListViewPreference;

/**
 * Created by apple on 2017/2/19.
 */
public class WeChatSettingActivity extends AppCompatPreferenceActivity {
    private static ListViewPreference mListViewPreferenceIgnore;//过滤词列表
    private static ListViewPreference mListViewPreferenceAnswer;//回复语列表

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
    }

    private void initViews() {
        mListViewPreferenceIgnore = (ListViewPreference) findPreference("wechat_list_ignores");
        mListViewPreferenceAnswer = (ListViewPreference) findPreference("wechat_list_answers");
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
                case "wechat_edit_ignores":
                    if(mListViewPreferenceIgnore != null){
                        mListViewPreferenceIgnore.addItem(stringValue);
                    }
                    break;
                case "wechat_edit_answers":
                    if(mListViewPreferenceAnswer != null){
                        mListViewPreferenceAnswer.addItem(stringValue);
                    }
                    break;
            }

            return true;
        }
    };

}
