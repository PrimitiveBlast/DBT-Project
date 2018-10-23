package com.example.stratos.dbtv02;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomListAdapter extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] data;
    private final String[] date;
    private final String[] types;

    public CustomListAdapter(Activity context,String [] data,String[] date, String[] types) {
        super(context, R.layout.mylist, data);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.data=data;
        this.date=date;
        this.types=types;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.id);
        TextView txtValue = (TextView) rowView.findViewById(R.id.value);
        TextView txtDate = (TextView) rowView.findViewById(R.id.date);

        txtTitle.setText(Integer.toString(position+1));
        txtValue.setText(data[position]);
        txtDate.setText(date[position] + "   " + types[position]);

        return rowView;

    };

}

