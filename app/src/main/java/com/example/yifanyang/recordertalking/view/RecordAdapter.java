package com.example.yifanyang.recordertalking.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yifanyang.recordertalking.MainActivity;
import com.example.yifanyang.recordertalking.R;

import java.util.List;

/**
 * Created by yifanyang on 16/8/7.
 */

public class RecordAdapter extends ArrayAdapter<MainActivity.Recorder> {


    private Context mContext;

    private int mMinItemWidth;
    private int mMaxItemWidth;

    private LayoutInflater mInflater;

    public RecordAdapter(Context context, List<MainActivity.Recorder> datas) {
        super(context, -1,datas);

        mInflater = LayoutInflater.from(context);

        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        mMaxItemWidth= (int) (outMetrics.widthPixels * 0.8f);
        mMinItemWidth= (int) (outMetrics.widthPixels * 0.15f);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView == null){
            convertView=mInflater.inflate(R.layout.item_record,parent,false);
            holder = new ViewHolder();
            holder.seconds = (TextView) convertView.findViewById(R.id.id_recorder_time);
            holder.length=convertView.findViewById(R.id.id_recorder_length);

            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        holder.seconds.setText(Math.round(getItem(position).getTime()) + "\"");
        ViewGroup.LayoutParams lp=holder.length.getLayoutParams();
        lp.width= (int) (mMinItemWidth +(mMaxItemWidth/60f * getItem(position).getTime()));

        return convertView;
    }

    private  class ViewHolder{
        TextView seconds;
        View length;
    }
}
