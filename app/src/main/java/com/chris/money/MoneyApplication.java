package com.chris.money;
/**
 * User: xiaoming
 * Date: 2017-02-17
 * Time: 16:44
 * 描述一下这个类吧
 */

import android.app.Application;

/**
 * Created by apple on 2017/2/17.
 */
public class MoneyApplication extends Application {
    private static MoneyApplication mInstance;

    public static MoneyApplication getInstance(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

}
