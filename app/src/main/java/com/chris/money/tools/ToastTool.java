package com.chris.money.tools;
/**
 * User: xiaoming
 * Date: 2017-02-17
 * Time: 16:40
 * Toast工具类
 */

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.money.MoneyApplication;
import com.chris.money.R;

/**
 * Created by apple on 2017/2/17.
 */
public class ToastTool {
    private static Toast mInstance;
    private static TextView mTextView;

    private static void init(){
        mInstance = new Toast(MoneyApplication.getInstance().getApplicationContext());
        mInstance.setDuration(Toast.LENGTH_LONG);
        mInstance.setGravity(Gravity.BOTTOM, 0 ,0);
        View view = LayoutInflater.from(MoneyApplication.getInstance()).inflate(R.layout.layout_textview_toast, null);
        mTextView = (TextView) view.findViewById(R.id.tv_text);
        mInstance.setView(view);
    }



    public static void showSuccess(String message){
        if(mInstance == null) init();
        mTextView.setText(message);
        mTextView.getBackground().setLevel(1);
        mInstance.show();
    }

    public static void showWarn(String message){
        if(mInstance == null) init();
        mTextView.setText(message);
        mTextView.getBackground().setLevel(2);
        mInstance.show();
    }

    public static void showError(String message){
        if(mInstance == null) init();
        mTextView.setText(message);
        mTextView.getBackground().setLevel(3);
        mInstance.show();
    }
}
