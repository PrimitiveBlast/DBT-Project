package com.example.stratos.dbtv02;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TheBook extends AppCompatActivity {

    Book_Table table;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_book);

        Bundle extras = getIntent().getExtras();
        String startDate = extras.getString("date1");
        String endDate = extras.getString("date2");
        Date start, end;
        start = new Date();
        end = new Date();
        try {
            start = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(startDate);
            end = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        StringBuffer buffer = new StringBuffer();

        myDb = new DatabaseHelper(this);
        ListView list  = (ListView)findViewById(R.id.list1);
        ArrayList<StringBuffer> weeks = new ArrayList<>();
        ArrayList<String> weeksText = new ArrayList<>();

        String[] dates = getCol(1).toArray(new String[0]);
        String[] values = getCol(2).toArray(new String[0]);
        String[] types = getCol(3).toArray(new String[0]);
        String[] days = getCol(4).toArray(new String[0]);

        Date currentDate = new Date();
        Date previousDate = new Date();
        Calendar a = Calendar.getInstance();
        Calendar b = Calendar.getInstance();
        int week = 1;
        for (int i=0; i<dates.length; i++){
            dates[i] = dates[i].substring(0,10);
            try {
                currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(dates[i]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (i>0){
                a.setTime(currentDate);
                try {
                    previousDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(dates[i-1]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if ((currentDate.after(start) && currentDate.before(end)) || currentDate.equals(start) || currentDate.equals(end)){

                b.setTime(previousDate);
                int week1 = a.get(Calendar.WEEK_OF_YEAR);
                int week2 = b.get(Calendar.WEEK_OF_YEAR);
                if (week1 != week2){
                    weeks.add(buffer);
                    weeksText.add(buffer.toString());
                    buffer = new StringBuffer();
                    week++;
                }
                buffer.append("Week" + week + " " + dates[i] + " " + values[i] + " " + types[i] + " " + days[i] + "\n");
            }
        }
        weeksText.add(buffer.toString());

        String[] temporary = weeksText.toArray(new String[0]);
        table  = new Book_Table(this,temporary);
        list.setAdapter(table);



    }


    public ArrayList<String> getCol(int col){

        ArrayList<String> array = new ArrayList<>();

        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // show message
            Toast.makeText(TheBook.this, "No Measurements", Toast.LENGTH_SHORT).show();

            return null;
        }
        while (res.moveToNext()) {
            array.add(res.getString(col));
        }

        return array;
    }
}
