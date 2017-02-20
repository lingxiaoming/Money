package com.chris.money.adapter;
/**
 * User: xiaoming
 * Date: 2017-02-19
 * Time: 14:30
 * TextView      delete
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chris.money.R;

import java.util.List;

/**
 * Created by apple on 2017/2/19.
 */
public class TextViewAndDeleteAdapter extends BaseAdapter{
    private List<String> datas;
    private LayoutInflater mInflater;

    public TextViewAndDeleteAdapter(Context context, List<String> lists){
        mInflater = LayoutInflater.from(context);
        datas = lists;
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public String getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void addItem(String word){
        if(!datas.contains(word)){
            datas.add(word);
            notifyDataSetChanged();
        }
    }

    public void removeItem(String word){
        if(datas.contains(word)){
            datas.remove(word);
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.layout_item_text_delete, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_delete);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
            viewHolder.textView.setText(datas.get(i));

        return convertView;
    }

    class ViewHolder{
        public ImageView imageView;
        public TextView textView;
    }
}
