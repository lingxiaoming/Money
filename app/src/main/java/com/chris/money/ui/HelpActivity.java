package com.chris.money.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.chris.money.R;

public class HelpActivity extends AppCompatActivity {

    public static void go(Context context){
        Intent intent = new Intent(context, HelpActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("使用帮助");//
        setContentView(R.layout.activity_help);
    }
}
