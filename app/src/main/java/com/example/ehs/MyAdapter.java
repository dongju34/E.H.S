package com.example.ehs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

// MyAdapter.java
public class MyAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> dataList;

    public MyAdapter(Context context, ArrayList<String> dataList) {
        super(context, R.layout.custom_list_item, dataList);
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            // 뷰 홀더 패턴을 사용하여 성능 향상
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_list_item, parent, false);
            viewHolder.textView = convertView.findViewById(R.id.textItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 데이터를 가져와서 리스트뷰 아이템에 설정
        String data = dataList.get(position);
        viewHolder.textView.setText(data);

        return convertView;
    }

    private static class ViewHolder {
        TextView textView;
    }
}