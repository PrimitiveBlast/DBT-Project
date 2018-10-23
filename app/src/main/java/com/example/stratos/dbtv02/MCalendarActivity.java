package com.example.stratos.dbtv02;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MCalendarActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_mcalendar);
        myDb = new DatabaseHelper(this);
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView3);
        date = (TextView)findViewById(R.id.textView2);

        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);// this will for example return 2 for tuesday
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        date.setText(ConvertWeekDate(dayOfWeek,dayOfMonth));


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                Calendar c = Calendar.getInstance();
                c.set(year, month, dayOfMonth);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK); // this will for example return 2 for tuesday
                date.setText(ConvertWeekDate(dayOfWeek,dayOfMonth));

                viewDayData(dayOfMonth,month,year);
            }
        });

    }
    public String ConvertWeekDate(int dayOfWeek,int dayOfMonth){
        if(dayOfWeek == 1){
           return "SUN " +dayOfMonth;
        }
        else if(dayOfWeek == 2){
            return "MON " +dayOfMonth;
        }
        else if(dayOfWeek == 3){
            return "TUE " +dayOfMonth;
        }
        else if(dayOfWeek == 4){
            return "WED " +dayOfMonth;
        }
        else if(dayOfWeek == 5){
            return "THU " +dayOfMonth;
        }
        else if(dayOfWeek == 6){
            return "FRI " +dayOfMonth;
        }
        else{
            return "SAT " +dayOfMonth;

        }

    }
    public void viewAll() {


        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // show message
            Toast.makeText(MCalendarActivity.this, "No Measurements", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<String> list = new ArrayList<String>();

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {

            buffer.append("Date :"+ res.getString(1)+"\n");
            buffer.append("Value :"+ res.getString(2)+"\n");

            list.add(buffer.toString());
            buffer.delete(0, buffer.length());
        }

        // Show all data
        showMessage("Measurements",list);


         }

    public void viewDayData(int day ,int month,int year) {
        String Current_Date;
        String[] splits;
        month ++;
        if(day<10){
            if(month<10){
                Current_Date = "0"+day+"-"+"0"+month+"-"+year;
            }
            else{
                Current_Date = "0"+day+"-"+month+"-"+year;
            }
        }else{
            if(month<10){
                Current_Date = day+"-"+"0"+month+"-"+year;
            }
            else{
                Current_Date = day+"-"+month+"-"+year;
            }
        }



        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // show message
            Toast.makeText(MCalendarActivity.this, "No Measurements", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<String> list = new ArrayList<String>();

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append(res.getString(1).substring(11,16)+"\n");
            buffer.append(res.getString(2)+" mg/dl"+"\n");
            splits = res.getString(1).split(" ");

            if (Current_Date.equals(splits[0])){
                list.add(buffer.toString());
            }

            buffer.delete(0, buffer.length());
        }

        // Show all data
        showMessage("Measurements",list);


    }



    public void showMessage(String title,ArrayList<String> list){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);

        builder.setItems(list.toArray(new String[list.size()]), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

}
