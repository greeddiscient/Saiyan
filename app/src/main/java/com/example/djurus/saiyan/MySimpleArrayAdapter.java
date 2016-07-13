package com.example.djurus.saiyan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by djurus on 7/13/16.
 */
public class MySimpleArrayAdapter extends ArrayAdapter<Quest> {
    private final Context context;
    private final ArrayList<Quest> values;

    public MySimpleArrayAdapter(Context context, ArrayList<Quest> values) {
        super(context, R.layout.questview, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.questview, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.questname);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.category);
        textView.setText(values.get(position).getQuestName());
        String category = values.get(position).getCategory();
        if (category.equals("basketball")) {
            imageView.setImageResource(R.drawable.basketball);
        } else if (category.equals("golf")) {
            imageView.setImageResource(R.drawable.golfball);
        }
        else if (category.equals("weights")) {
            imageView.setImageResource(R.drawable.weights);
        }

        return rowView;
    }
}