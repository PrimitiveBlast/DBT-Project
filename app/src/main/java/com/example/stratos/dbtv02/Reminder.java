package com.example.stratos.dbtv02;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Reminder extends AppCompatActivity  implements
        ReminderListAdapter.customSwitchListener {

    ListView list;
    ArrayList<String> arrayList ;
    DatabaseHelper myDb;
    String[] stringArray;
    String[] state;
    String[] ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_reminder);


        //==========================ADD NEW CLOCK SECTION ============================================================================

        myDb = new DatabaseHelper(this);

        list = (ListView)findViewById(R.id.list1);
        arrayList = new ArrayList<>();

        final Button btnAdd = (Button)findViewById(R.id.displayBtn);
        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
                animation.setDuration(300); // duration - half a second
                animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate

                btnAdd.startAnimation(animation);
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Reminder.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        if(selectedMinute<10){
                            if(selectedHour<10){
                                myDb.insertTime("0" + Integer.toString(selectedHour) + ":0" + Integer.toString(selectedMinute));
                            }
                            else {
                                myDb.insertTime(Integer.toString(selectedHour) + ":0" + Integer.toString(selectedMinute));
                            }
                        }
                        else{
                            if(selectedHour<10) {
                                myDb.insertTime("0" + Integer.toString(selectedHour) + ":" + Integer.toString(selectedMinute));
                            }
                            else{
                                myDb.insertTime(Integer.toString(selectedHour) + ":" + Integer.toString(selectedMinute));
                            }
                        }

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        //----------------------------------------------------------------------------------------------------------------------

            ArrayList<String> times = getTimes();
            if(times != null){
                stringArray = getTimes().toArray(new String[0]);
                state = getState().toArray(new String[0]);
                ids = getIdTime().toArray(new String[0]);
                ReminderListAdapter adapter = new ReminderListAdapter(this, stringArray, state);
                adapter.setCustomSwitchListener(Reminder.this);



                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView adapterView, View view, final int position, long id) {

                        AlertDialog alertDialog = new AlertDialog.Builder(Reminder.this).create();
                        alertDialog.setTitle("Delete?");

                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        myDb.deleteTime(ids[position]);
                                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
                                        notificationIntent.addCategory("android.intent.category.DEFAULT");
                                        PendingIntent broadcast = PendingIntent.getBroadcast(getBaseContext(), Integer.parseInt(ids[position]), notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                                        broadcast.cancel();
                                        alarmManager.cancel(broadcast);
                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();



                    }
                });

                list.setAdapter(adapter);
            }
    }

    //================================================================================METHODS
    public ArrayList<String> getTimes(){
        ArrayList<String> times = new ArrayList<String>();

        Cursor res = myDb.getAllTimes();
        if(res.getCount() == 0) {
            // show message
            Toast.makeText(Reminder.this, "No notifications set", Toast.LENGTH_SHORT).show();

            return null;
        }

        while (res.moveToNext()) {
            times.add(res.getString(1));

        }

        return times;
    }

    public ArrayList<String> getIdTime(){

        ArrayList<String> ids = new ArrayList<String>();

        Cursor res = myDb.getAllTimes();
        if(res.getCount() == 0) {


            return null;
        }

        while (res.moveToNext()) {
            ids.add(res.getString(0));

        }
        return ids;
    }

    public ArrayList<String> getState(){

        ArrayList<String> state = new ArrayList<String>();

        Cursor res = myDb.getAllTimes();
        if(res.getCount() == 0) {


            return null;
        }

        while (res.moveToNext()) {
            state.add(res.getString(2));

        }
        return state;
    }

    @Override
    public void onSwitchClickListener(int position, boolean thisstate) {
        if (thisstate == true){
            state[position] = "1";
            int hour = Integer.parseInt(stringArray[position].substring(0,2));
            int minutes = Integer.parseInt(stringArray[position].substring(3,5));
            setNotification(hour, minutes, Integer.parseInt(ids[position]));
        }
        else{
            state[position] = "0";
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
            notificationIntent.addCategory("android.intent.category.DEFAULT");
            PendingIntent broadcast = PendingIntent.getBroadcast(this, Integer.parseInt(ids[position]), notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            broadcast.cancel();
            alarmManager.cancel(broadcast);
        }
        myDb.updateState(Integer.parseInt(state[position]), Integer.parseInt(ids[position]));
        Intent intent = getIntent();
        finish();
        startActivity(intent);

    }

    public void setNotification(int hour, int minutes, int id){

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");
        PendingIntent broadcast = PendingIntent.getBroadcast(this,  id, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE,minutes);
        cal.set(Calendar.SECOND,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, broadcast);

    }

}
