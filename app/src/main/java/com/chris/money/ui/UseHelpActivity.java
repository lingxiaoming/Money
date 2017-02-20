package com.chris.money.ui;
/**
 * User: xiaoming
 * Date: 2017-02-18
 * Time: 20:58
 * 用户说明页面
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.chris.money.R;

/**
 * Created by apple on 2017/2/18.
 */
public class UseHelpActivity extends AppCompatActivity {

    public static void go(Context context){
        Intent intent = new Intent(context, UseHelpActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_help);
    }
}
