package com.example.stratos.dbtv02;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Measurment_List extends AppCompatActivity {

    ListView mlist ;
    CustomListAdapter listadapter;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_measurment__list);

        myDb = new DatabaseHelper(this);

        mlist = (ListView)findViewById(R.id.list1);
        final String[] ids = get_col(0);
        String[] date = get_col(1);
        String[] mes = get_col(2);
        String[] types = get_col(3);

        if(date==null){
            return;

        }

        listadapter = new CustomListAdapter(this, mes,date, types);
        mlist.setAdapter(listadapter);
        mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView adapterView, View view, final int position, long id) {

                AlertDialog alertDialog = new AlertDialog.Builder(Measurment_List.this).create();
                alertDialog.setTitle("Delete?");
                //alertDialog.setMessage("Alert message to be shown");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            myDb.deleteData(ids[position]);
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });



    }

    protected String[] get_col (int col){
     //   arrayList = new ArrayList<String>();
        String data[] = new String[20];

        for (int i =0; i<20; i++){
            data[i]= " ";
        }

        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // show message
            Toast.makeText(Measurment_List.this, "No Measurements", Toast.LENGTH_SHORT).show();

            return null;
        }



        res.moveToLast();

        if(col == 2) {
            data[0] = res.getString(col) + " mg/dl";
            int i = 1;
            while (res.moveToPrevious()) {

                data[i] = res.getString(col) + " mg/dl";

                i++;
                if (i == 20) break;
            }

        }else {
            data[0] = res.getString(col);
            int i = 1;
            while (res.moveToPrevious()) {

                data[i] = res.getString(col);

                i++;
                if (i == 20) break;
            }
        }
            return data;
    }

}
