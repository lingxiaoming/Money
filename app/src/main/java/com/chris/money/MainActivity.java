package com.chris.money;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.chris.money.tools.ToastTool;
import com.chris.money.ui.OtherSettingActivity;
import com.chris.money.ui.UseHelpActivity;
import com.chris.money.ui.WeChatSettingActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private ToggleButton mToggleButton;//开启、关闭按钮
    private ImageView mImageViewMood;//激活状态图标
    private DrawerLayout mDrawer;//打开、关闭导航栏


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "立即付费激活", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initViews();
    }

    private void initViews() {
        mToggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        mToggleButton.setOnCheckedChangeListener(this);
        findViewById(R.id.ll_statistics).setOnClickListener(this);
        findViewById(R.id.ll_register).setOnClickListener(this);
        findViewById(R.id.ll_setting).setOnClickListener(this);
        mImageViewMood = (ImageView) findViewById(R.id.iv_register);
        mImageViewMood.setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.setting_wx) {
            ToastTool.showSuccess("微信红包设置");
            WeChatSettingActivity.go(this);
//            Intent intent = new Intent(this, SettingsActivity.class);
//            startActivity(intent);
        } else if (id == R.id.setting_qq) {
            ToastTool.showWarn("QQ红包设置");
        } else if (id == R.id.wechat_statistics) {
            ToastTool.showError("微信红包统计");
        } else if(id == R.id.qq_statistics){
            ToastTool.showError("QQ红包统计");
        } else if (id == R.id.setting_other) {
            OtherSettingActivity.go(this);
        } else if (id == R.id.about) {
            UseHelpActivity.go(this);
        } else if (id == R.id.send) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
//                shareIntent.putExtra(Intent.EXTRA_TEXT, "123");
//                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "快点抢红包");
            shareIntent.putExtra("Kdescription", "快点抢红包神器，官方下载地址http://zhushou.360.cn/detail/index/soft_id/3299276");//微信朋友圈专用

            copyResToSdcard(Environment.getExternalStorageDirectory().getPath());
            Uri image = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/qrcode.png"));
//                Uri uri = Uri.parse("file:///android_asset/qrcode.png");
//                Toast.makeText(getActivity(), ""+Environment.getExternalStorageDirectory().getPath(), Toast.LENGTH_LONG).show();
            shareIntent.putExtra(Intent.EXTRA_STREAM, image);

            //设置分享列表的标题，并且每次都显示分享列表
            startActivity(Intent.createChooser(shareIntent, "分享到"));
        } else if (id == R.id.use_describe) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            ToastTool.showSuccess("已经开启");
        } else {
            ToastTool.showSuccess("已经关闭");
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ll_statistics:

                break;
            case R.id.ll_register:
                ToastTool.showError("我要激活");
//                mImageViewMood.setEnabled(true);
                mImageViewMood.setImageResource(R.drawable.ic_happy);
                break;
            case R.id.ll_setting:
                mDrawer.openDrawer(GravityCompat.START);
                break;
        }
    }

    /*
* 将raw里的文件copy到sd卡下
* */
    public void copyResToSdcard(String name) {//name为sd卡下制定的路径
        Field[] raw = R.raw.class.getFields();
        for (Field r : raw) {
            try {
                //     System.out.println("R.raw." + r.getName());
                int id = getResources().getIdentifier(r.getName(), "raw", MoneyApplication.getInstance().getPackageName());
                if (r.getName().equals("qrcode")) {
                    String path = name + "/" + r.getName() + ".png";
                    File file = new File(path);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    BufferedOutputStream bufEcrivain = new BufferedOutputStream((new FileOutputStream(file)));
                    BufferedInputStream VideoReader = new BufferedInputStream(getResources().openRawResource(id));
                    byte[] buff = new byte[20 * 1024];
                    int len;
                    while ((len = VideoReader.read(buff)) > 0) {
                        bufEcrivain.write(buff, 0, len);
                    }
                    bufEcrivain.flush();
                    bufEcrivain.close();
                    VideoReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
