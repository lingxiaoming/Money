package com.chris.money.ui;
/**
 * User: xiaoming
 * Date: 2017-02-19
 * Time: 10:46
 * 其他设置页面
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.chris.money.R;

/**
 * Created by apple on 2017/2/19.
 */
public class OtherSettingActivity extends AppCompatPreferenceActivity {

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

}
