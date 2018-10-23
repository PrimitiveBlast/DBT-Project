package com.example.stratos.dbtv02;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Stats extends AppCompatActivity {

    Spinner spinner;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        myDb = new DatabaseHelper(this);

        spinner = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<>();
        list.add("Day");
        list.add("Week");
        list.add("Month");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    ShowGraph(1);
                }
                else if (position == 1) {
                    ShowGraph(7);
                }
                else {
                    ShowGraph(30);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public void ShowGraph (int days){

        int limit=0;
        int i=1;

        LineChart lineChart = (LineChart) findViewById(R.id.chart);
        ArrayList<String> check = getCol(1);
        if (check == null) return;

        String[] values = getCol(2).toArray(new String[0]);
        String[] times = getCol(1).toArray(new String[0]);

        int cdate, bcdate;

        while (i<=days)
        {
            cdate=Integer.parseInt(times[times.length-limit-1].substring(0,2));
            bcdate = Integer.parseInt(times[times.length-limit-2].substring(0,2));
            if(cdate!=bcdate){
                i++;
            }
            limit++;
        }

        ArrayList<Entry> entries = new ArrayList<>();
        i=0;
        while(i<limit) {
            entries.add(new Entry(Integer.parseInt(values[values.length - limit + i]), i));
            i++;
        }

        LineDataSet dataset = new LineDataSet(entries, "mg/dl");

        ArrayList<String> labels = new ArrayList<>();

        labels.add(times[times.length - limit].substring(0,5));
        i=1;
        while(i<limit) {
            if(times[times.length - limit + i].substring(0, 5).equals(times[times.length - limit + i-1].substring(0, 5))){
                labels.add(" ");
            }
            else{
                labels.add(times[times.length - limit + i].substring(0, 5));
            }
            i++;
        }

        LineData data = new LineData(labels, dataset);
        dataset.setDrawFilled(true);

        lineChart.setData(data);
        lineChart.animateY(3000);
        lineChart.animateX(3000);
        lineChart.setGridBackgroundColor(Color.TRANSPARENT);
    }


    public ArrayList<String> getCol(int col){
        ArrayList<String> values = new ArrayList<>();

        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // show message
            //Toast.makeText(Stats.this, "No measurements", Toast.LENGTH_SHORT).show();

            return null;
        }

        while (res.moveToNext()) {
            values.add(res.getString(col));
        }

        return values;
    }

}
