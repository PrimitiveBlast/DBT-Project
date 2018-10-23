package com.example.stratos.dbtv02;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Stratos on 11/18/2016.
 */

public class Tab3Book extends Fragment {

    EditText date1,date2;
    DatabaseHelper myDb;
    Calendar myCalendar,temp;
    Calendar myCalendar2;
    Button  display;
    DatePickerDialog.OnDateSetListener datepicker1;
    DatePickerDialog.OnDateSetListener datepicker2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_book, container, false);
        myDb = new DatabaseHelper(getContext());
        date1 = (EditText)rootView.findViewById(R.id.date1);
        date2 = (EditText)rootView.findViewById(R.id.date2);
        display=(Button)rootView.findViewById(R.id.displayBtn) ;

        Cursor res = myDb.getAllData();

        if(res.getCount() == 0) {
            // show message
            Toast.makeText(getContext(), "No Measurements", Toast.LENGTH_SHORT).show();
            return rootView;
        }

        res.moveToNext();
        String firstDate = res.getString(1).substring(0,10);
        date1.setText(firstDate);
        String [] parts = firstDate.split("-");
        myCalendar = Calendar.getInstance();
        temp = Calendar.getInstance();
        temp.set(Calendar.YEAR, Integer.parseInt(parts[2]));
        temp.set(Calendar.MONTH, Integer.parseInt(parts[1])-1);
        temp.set(Calendar.DAY_OF_MONTH,Integer.parseInt(parts[0]));
        myCalendar=temp;
        myCalendar2 = Calendar.getInstance();

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
                animation.setDuration(300); // duration - half a second
                animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate

                display.startAnimation(animation);

                if(date1.getText().toString().equals("Select Date")){
                    Toast.makeText(getContext(),"You must select a proper Date",Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(date2.getText().toString().equals("Select Date")){
                    Toast.makeText(getContext(),"You must select a proper Date",Toast.LENGTH_SHORT).show();
                    return;
                }

                long time1 = myCalendar.getTimeInMillis();
                long time2 = myCalendar2.getTimeInMillis();

                if(time1>time2){
                    Toast.makeText(getContext(),"Second Date must be after the first one",Toast.LENGTH_SHORT).show();
                    return;

                }

                Intent intent = new Intent(getContext(),TheBook.class);
                intent.putExtra("date1", date1.getText().toString());
                intent.putExtra("date2", date2.getText().toString());
                startActivity(intent);
            }
        });

      datepicker1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                long time1 = myCalendar.getTimeInMillis();
                long time2 = temp.getTimeInMillis();
                updateLabel();
//                if (time1>time2){
//                    updateLabel();
//                }
//                  else{
//                    Toast.makeText(getContext(),"No entries on that Date",Toast.LENGTH_SHORT).show();
//
//                }
            }

        };

        datepicker2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();

            }

        };


        date1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), datepicker1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        date2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), datepicker2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        return rootView;
    }


    private void updateLabel() {

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        date1.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        date2.setText(sdf.format(myCalendar2.getTime()));
    }

}