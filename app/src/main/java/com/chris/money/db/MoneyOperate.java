package com.chris.money.db;
/**
 * User: xiaoming
 * Date: 2017-02-20
 * Time: 16:46
 * 红包数据库操作
 */

import com.chris.money.bean.DayCount;
import com.chris.money.bean.Money;
import com.chris.money.tools.DateFormatTool;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by apple on 2017/2/20.
 */
public class MoneyOperate {
    private static MoneyOperate instance;
    private Realm mRealm;
    private static final int MAX_NUMBER = 5;//最多取出5条数据，可以按天，按月

    private MoneyOperate() {
        mRealm = Realm.getDefaultInstance();
    }

    public void initRealm() {
        mRealm = Realm.getDefaultInstance();
    }

    public static MoneyOperate getInstance() {
        if (instance == null) {
            instance = new MoneyOperate();
        }
        return instance;
    }

    public void cancelDB(){
        mRealm.close();
    }

    public void addWeChatMoney(final Money money) {//抢到微信红包,记录到数据库
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mRealm.copyToRealm(money);
            }
        });
    }

    public void deleteWeChatMoney() {//删除一个微信红包记录

    }

    public void updateWeChatMoney() {//修改一个微信红包记录

    }

    public void getWeChatMoney(RealmChangeListener callback) {
        RealmResults<Money> result = mRealm.where(Money.class).findAllAsync();
        result.addChangeListener(callback);
    }

    public List<DayCount> get7DayWechatMoneyCountList(long timeMillion) {//得到按天算的红包统计列表,往回统计7天
        List<DayCount> list = new ArrayList<>();
        long tempTimeMillion = timeMillion;
        for (int i = 0; i < MAX_NUMBER; i++) {
            long timeDay = DateFormatTool.getDay(tempTimeMillion);
            Number number = mRealm.where(Money.class).equalTo("type", 0).equalTo("timeDay", timeDay).sum("money");
            long count = mRealm.where(Money.class).equalTo("type", 0).equalTo("timeDay", timeDay).count();
            DayCount dayCount = new DayCount();
            dayCount.counts = count;
            dayCount.moneys = number.floatValue();
            dayCount.day = timeDay;

            list.add(dayCount);

            tempTimeMillion -= 24 * 60 * 60 * 1000;
        }

        return list;
    }
}
