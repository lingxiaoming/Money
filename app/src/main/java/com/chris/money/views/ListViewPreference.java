package com.chris.money.views;
/**
 * User: xiaoming
 * Date: 2017-02-19
 * Time: 15:52
 * 自定义Preference，里面只有一个ListView
 */

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.chris.money.R;
import com.chris.money.adapter.TextViewAndDeleteAdapter;
import com.chris.money.tools.PreferenceTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/2/19.
 */
public class ListViewPreference extends Preference implements AdapterView.OnItemClickListener {
    private NoScrollListView mListView;
    private TextViewAndDeleteAdapter mTextViewAndDeleteAdapter;
    private List<String> datas = new ArrayList<>();
    private String preferenceKey;//首选项的key作为保存本地preference的key

    public ListViewPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutResource(R.layout.layout_listview);
        initDatas();
    }

    public ListViewPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListViewPreference(Context context) {
        super(context, null, 0);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        return super.onCreateView(parent);
    }


    private void initDatas() {
        preferenceKey = getKey();
        datas = PreferenceTool.getInstance().getStringList(preferenceKey);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
//        if (mListView == null) {
        mListView = (NoScrollListView) view.findViewById(android.R.id.list);
        mTextViewAndDeleteAdapter = new TextViewAndDeleteAdapter(getContext(), datas);
        mListView.setAdapter(mTextViewAndDeleteAdapter);
        mListView.setOnItemClickListener(this);
//        }
    }

    public void addItem(String word) {
        mTextViewAndDeleteAdapter.addItem(word);
        List<String> stringList = PreferenceTool.getInstance().getStringList(preferenceKey);
        if (stringList != null) {
            if (!stringList.contains(word)) {
                stringList.add(word);
            } else {
                return;
            }
        } else {
            stringList = new ArrayList<>();
            stringList.add(word);
        }
        PreferenceTool.getInstance().saveStringList(preferenceKey, stringList);
    }

    public void removeItem(String word) {
        mTextViewAndDeleteAdapter.removeItem(word);
        List<String> stringList = PreferenceTool.getInstance().getStringList(preferenceKey);
        if (stringList != null) {
            if (stringList.contains(word)) {
                stringList.remove(word);
                PreferenceTool.getInstance().saveStringList(preferenceKey, stringList);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        removeItem(mTextViewAndDeleteAdapter.getItem(i));
    }
}
