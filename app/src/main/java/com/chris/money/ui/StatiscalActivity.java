package com.chris.money.ui;
/**
 * User: xiaoming
 * Date: 2017-02-20
 * Time: 14:00
 * 统计页面
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.chris.money.R;
import com.chris.money.bean.DayCount;
import com.chris.money.bean.Money;
import com.chris.money.db.MoneyOperate;
import com.chris.money.tools.DateFormatTool;
import com.chris.money.views.BarChartView;

import java.util.List;
import java.util.Random;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by apple on 2017/2/20.
 */
public class StatiscalActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnAdd;
    private BarChartView mBarChartView;
    private TextView mTvType;
    private int type;//0按天显示，1按月显示

    public static void go(Context context) {
        Intent intent = new Intent(context, StatiscalActivity.class);
        context.startActivity(intent);
    }

    public static void goSingleTask(Context context) {
        Intent intent = new Intent(context, StatiscalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("红包统计");//
        setContentView(R.layout.activity_statistical);

        MoneyOperate.getInstance().initRealm();
        initViews();
        initDatas();
    }

    private void initDatas() {
        showMoneyStatiscalByDay();
    }

    private void initViews() {
        mBarChartView = (BarChartView) findViewById(R.id.bar_chart);

        btnAdd = (Button) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);

        mTvType = (TextView) findViewById(R.id.tv_type);
        mTvType.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_add:
                Money money = new Money();
                money.timeLong = System.currentTimeMillis();
                money.timeYear = DateFormatTool.getYear(money.timeLong);
                money.timeMonth = DateFormatTool.getMonth(money.timeLong);
                money.timeDay = DateFormatTool.getDay(money.timeLong);
                money.money = new Random().nextFloat() * 10;
                money.name = "李四";
                money.type = 0;
                MoneyOperate.getInstance().addWeChatMoney(money);
                break;
            case R.id.tv_type:
                type = 1-type;
                if(type == 0){
                    showMoneyStatiscalByDay();
                    mTvType.setText("按天");
                }else {
                    showMoneyStatiscalByMonth();
                    mTvType.setText("按月");
                }

                break;
        }
    }

    private void showMoneyStatiscalByDay() {
        MoneyOperate.getInstance().getWeChatMoney(new RealmChangeListener<RealmResults<Money>>() {
            @Override
            public void onChange(RealmResults<Money> results) {
                int m = results.size();
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < m; i++) {
                    sb.append(results.get(i).money + "\n");
                }
                List<DayCount> list = MoneyOperate.getInstance().get7DayWechatMoneyCountList(System.currentTimeMillis());
                if (list != null && list.size() > 0) {
                    BarChartView.BarChartItemBean[] items = new BarChartView.BarChartItemBean[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        DayCount dayCount = list.get(i);
                        String showDate = (dayCount.day == DateFormatTool.getDay(System.currentTimeMillis())) ? "今天" :
                                (dayCount.day == DateFormatTool.getDay(System.currentTimeMillis() - 24 * 60 * 60 * 1000)) ? "昨天" : dayCount.day + "";
                        items[i] = new BarChartView.BarChartItemBean(showDate, dayCount.moneys, dayCount.counts);
                    }
                    mBarChartView.setItems(items);
                }

            }
        });
    }

    private void showMoneyStatiscalByMonth() {
        MoneyOperate.getInstance().getWeChatMoney(new RealmChangeListener<RealmResults<Money>>() {
            @Override
            public void onChange(RealmResults<Money> results) {
                int m = results.size();
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < m; i++) {
                    sb.append(results.get(i).money + "\n");
                }
                List<DayCount> list = MoneyOperate.getInstance().get7MonthWechatMoneyCountList(System.currentTimeMillis());
                if (list != null && list.size() > 0) {
                    BarChartView.BarChartItemBean[] items = new BarChartView.BarChartItemBean[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        DayCount dayCount = list.get(i);
                        items[i] = new BarChartView.BarChartItemBean(dayCount.month+"", dayCount.moneys, dayCount.counts);
                    }
                    mBarChartView.setItems(items);
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MoneyOperate.getInstance().cancelDB();
    }
}
