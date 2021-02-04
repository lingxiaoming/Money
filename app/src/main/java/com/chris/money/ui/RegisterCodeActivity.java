package com.chris.money.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.chris.money.MoneyApplication;
import com.chris.money.R;
import com.chris.money.constant.PreferenceConstant;
import com.chris.money.tools.PreferenceTool;
import com.chris.money.tools.ToastTool;

/**
 * 输入激活码页面
 */
public class RegisterCodeActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEditText;
    private Button mButton;


    public static void go(Context context) {
        Intent intent = new Intent(context, RegisterCodeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("激活");//
        setContentView(R.layout.activity_register_code);

        initViews();
    }

    private void initViews() {
        mEditText = (EditText) findViewById(R.id.et_code);
        mButton = (Button) findViewById(R.id.btn_code);

        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_code:

                MoneyApplication.userRegisterCode = mEditText.getText().toString();
                PreferenceTool.getInstance().saveString(PreferenceConstant.KEY_REGISTER_CODE, MoneyApplication.userRegisterCode);

                if(MoneyApplication.getInstance().hasRegiste()){
                    ToastTool.showSuccess("激活成功！");
                }else {
                    ToastTool.showError("激活失败！");
                }
                break;
        }
    }
}
