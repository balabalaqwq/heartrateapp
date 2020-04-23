package com.example.heartrateapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class HeartRateAdapter extends ArrayAdapter<HeartRate> {
    private int resourceId;

    public HeartRateAdapter(Context context, int textViewResourceId, List<HeartRate> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HeartRate heartRate = getItem(position);

        View view;

      ViewHolder viewHolder;

        if (convertView == null) {
//            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.time = (TextView) view.findViewById(R.id.time);
            viewHolder.heartrate = (TextView) view.findViewById(R.id.heartrate);
            viewHolder.status = (TextView) view.findViewById(R.id.status);
            view.setTag(viewHolder);


        } else  {
            view = convertView;

            viewHolder = (ViewHolder) view.getTag();

        }

        viewHolder.time.setText(heartRate.getTime());
        viewHolder.heartrate.setText(heartRate.getHeartrate());
        viewHolder.status.setText(heartRate.getStatus());

        return view;

    }

    class ViewHolder {
        TextView time;
        TextView heartrate;
        TextView status;
    }
}
