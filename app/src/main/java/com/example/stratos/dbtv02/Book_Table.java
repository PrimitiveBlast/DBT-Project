package com.example.stratos.dbtv02;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Book_Table extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] data;

    TextView[] textViews;
    TextView title;

    public Book_Table(Activity context,String [] data) {
        super(context, R.layout.book_adapter, data);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.data=data;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.book_adapter, null,true);

        textViews = new TextView[35];
        initializeTextviews(rowView);

        title = (TextView)rowView.findViewById(R.id.textView11);
        title.setText("Week " + Integer.toString(position+1));

        for (int i=0; i<28; i++){
            textViews[i].setText("-");
        }
        for (int i=28; i<35; i++){
            textViews[i].setText(" ");
        }

        String[] measurements = data[position].split("\n");
        String[] singleMeasure;
        singleMeasure = measurements[0].split(" ");
        String date = singleMeasure[1];
        Date a = new Date();
        Calendar c = Calendar.getInstance();

        try {
            a = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        c.setTime(a);

        int thisDay = c.get(Calendar.DAY_OF_WEEK);
        for (int i=thisDay; i>1; i--){
            c.add(Calendar.DATE, -1);
        }
        for (int i=1; i<8; i++){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            textViews[i+27].setText(sdf.format(c.getTime()).substring(0,5));
            c.add(Calendar.DATE, 1);
        }


        for (int i=0; i<measurements.length; i++){
            singleMeasure = measurements[i].split(" ");

            if(singleMeasure.length == 1) continue;
            String day = singleMeasure[4];
            String type = singleMeasure[3];

            if (type.equals("Breakfast")){
                switch (day) {
                    case "1":
                        textViews[0].setText(singleMeasure[2]);
                        break;
                    case "2":
                        textViews[1].setText(singleMeasure[2]);
                        break;
                    case "3":
                        textViews[2].setText(singleMeasure[2]);
                        break;
                    case "4":
                        textViews[3].setText(singleMeasure[2]);
                        break;
                    case "5":
                        textViews[4].setText(singleMeasure[2]);
                        break;
                    case "6":
                        textViews[5].setText(singleMeasure[2]);
                        break;
                    case "7":
                        textViews[6].setText(singleMeasure[2]);
                        break;
                }
            }
            else if (type.equals("Lunch")){
                switch (day) {
                    case "1":
                        textViews[7].setText(singleMeasure[2]);
                        break;
                    case "2":
                        textViews[8].setText(singleMeasure[2]);
                        break;
                    case "3":
                        textViews[9].setText(singleMeasure[2]);
                        break;
                    case "4":
                        textViews[10].setText(singleMeasure[2]);
                        break;
                    case "5":
                        textViews[11].setText(singleMeasure[2]);
                        break;
                    case "6":
                        textViews[12].setText(singleMeasure[2]);
                        break;
                    case "7":
                        textViews[13].setText(singleMeasure[2]);
                        break;
                }
            }
            else if (type.equals("Dinner")){
                switch (day) {
                    case "1":
                        textViews[14].setText(singleMeasure[2]);
                        break;
                    case "2":
                        textViews[15].setText(singleMeasure[2]);
                        break;
                    case "3":
                        textViews[16].setText(singleMeasure[2]);
                        break;
                    case "4":
                        textViews[17].setText(singleMeasure[2]);
                        break;
                    case "5":
                        textViews[18].setText(singleMeasure[2]);
                        break;
                    case "6":
                        textViews[19].setText(singleMeasure[2]);
                        break;
                    case "7":
                        textViews[20].setText(singleMeasure[2]);
                        break;
                }
            }
            else if (type.equals("Sleep")){
                switch (day) {
                    case "1":
                        textViews[21].setText(singleMeasure[2]);
                        break;
                    case "2":
                        textViews[22].setText(singleMeasure[2]);
                        break;
                    case "3":
                        textViews[23].setText(singleMeasure[2]);
                        break;
                    case "4":
                        textViews[24].setText(singleMeasure[2]);
                        break;
                    case "5":
                        textViews[25].setText(singleMeasure[2]);
                        break;
                    case "6":
                        textViews[26].setText(singleMeasure[2]);
                        break;
                    case "7":
                        textViews[27].setText(singleMeasure[2]);
                        break;
                }
            }
        }



        return rowView;

    };

    public void initializeTextviews(View rowView){
        textViews[0] = (TextView)rowView.findViewById(R.id.textView32);
        textViews[1] = (TextView)rowView.findViewById(R.id.textView33);
        textViews[2] = (TextView)rowView.findViewById(R.id.textView34);
        textViews[3] = (TextView)rowView.findViewById(R.id.textView35);
        textViews[4] = (TextView)rowView.findViewById(R.id.textView36);
        textViews[5] = (TextView)rowView.findViewById(R.id.textView37);
        textViews[6] = (TextView)rowView.findViewById(R.id.textView38);

        textViews[7] = (TextView)rowView.findViewById(R.id.textView42);
        textViews[8] = (TextView)rowView.findViewById(R.id.textView43);
        textViews[9] = (TextView)rowView.findViewById(R.id.textView44);
        textViews[10] = (TextView)rowView.findViewById(R.id.textView45);
        textViews[11] = (TextView)rowView.findViewById(R.id.textView46);
        textViews[12] = (TextView)rowView.findViewById(R.id.textView47);
        textViews[13] = (TextView)rowView.findViewById(R.id.textView48);

        textViews[14] = (TextView)rowView.findViewById(R.id.textView52);
        textViews[15] = (TextView)rowView.findViewById(R.id.textView53);
        textViews[16] = (TextView)rowView.findViewById(R.id.textView54);
        textViews[17] = (TextView)rowView.findViewById(R.id.textView55);
        textViews[18] = (TextView)rowView.findViewById(R.id.textView56);
        textViews[19] = (TextView)rowView.findViewById(R.id.textView57);
        textViews[20] = (TextView)rowView.findViewById(R.id.textView58);

        textViews[21] = (TextView)rowView.findViewById(R.id.textView62);
        textViews[22] = (TextView)rowView.findViewById(R.id.textView63);
        textViews[23] = (TextView)rowView.findViewById(R.id.textView64);
        textViews[24] = (TextView)rowView.findViewById(R.id.textView65);
        textViews[25] = (TextView)rowView.findViewById(R.id.textView66);
        textViews[26] = (TextView)rowView.findViewById(R.id.textView67);
        textViews[27] = (TextView)rowView.findViewById(R.id.textView68);

        textViews[28] = (TextView)rowView.findViewById(R.id.TextViewd2);
        textViews[29] = (TextView)rowView.findViewById(R.id.TextViewd3);
        textViews[30] = (TextView)rowView.findViewById(R.id.TextViewd4);
        textViews[31] = (TextView)rowView.findViewById(R.id.TextViewd5);
        textViews[32] = (TextView)rowView.findViewById(R.id.textViewd6);
        textViews[33] = (TextView)rowView.findViewById(R.id.textViewd7);
        textViews[34] = (TextView)rowView.findViewById(R.id.textViewd8);
    }
}

