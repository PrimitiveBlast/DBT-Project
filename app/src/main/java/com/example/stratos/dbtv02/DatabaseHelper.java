package com.example.stratos.dbtv02;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Measurements.db";
    public static final String TABLE_NAME = "Data";
    public static final String TABLE_NAME2 = "Notifications";
    public static final String TABLE_NAME3 ="Patient";
    public static final String TABLE_NAME4 ="Doctors";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "DATE";
    public static final String COL_3 = "VALUE";
    public static final String COL_4 = "TYPE";
    public static final String COL_5 = "DAY";

    public static final String COL_2_1 = "TIME";
    public static final String COL_2_2 = "ON_OFF";

    public static final String COL_3_1 = "NAME";
    public static final String COL_3_2 = "SURNAME";
    public static final String COL_3_3 = "ADDRESS";
    public static final String COL_3_4 = "HOMETOWN";
    public static final String COL_3_5 = "TK";
    public static final String COL_3_6 = "EMAIL";
    public static final String COL_3_7 = "PHONE";
    public static final String COL_3_8 = "CELLPHONE";

    public static final String COL_4_1 = "NAME";
    public static final String COL_4_2 = "SURNAME";
    public static final String COL_4_3 = "ADDRESS";
    public static final String COL_4_4 = "EMAIL";
    public static final String COL_4_5 = "PHONE";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 11);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,DATE DATETIME,VALUE INT, TYPE TEXT, DAY TEXT)");
        db.execSQL("create table " + TABLE_NAME2 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,TIME TEXT,ON_OFF INT)");
        db.execSQL("create table " + TABLE_NAME3 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT, SURNAME TEXT, ADDRESS TEXT, HOMETOWN TEXT, TK TEXT, EMAIL TEXT, PHONE TEXT, CELLPHONE TEXT)");
        db.execSQL("create table " + TABLE_NAME4 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT, SURNAME TEXT,ADDRESS TEXT, EMAIL TEXT, PHONE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME4);
        onCreate(db);
    }

    public boolean insertData(int value, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        contentValues.put(COL_2,dateFormat.format(date));
        contentValues.put(COL_3,value);
        contentValues.put(COL_4,type);
        contentValues.put(COL_5,Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }


    public boolean insertPersonalData(String name,String surname,String address,String hometown,String tk,String email,String phone,String cell  ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_3_1,name);
        contentValues.put(COL_3_2,surname);
        contentValues.put(COL_3_3,address);
        contentValues.put(COL_3_4,hometown);
        contentValues.put(COL_3_5,tk);
        contentValues.put(COL_3_6,email);
        contentValues.put(COL_3_7,phone);
        contentValues.put(COL_3_8,cell);

        long result = db.insert(TABLE_NAME3,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }



    public boolean insertTime(String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_1,time);
        contentValues.put(COL_2_2,0);
        long result = db.insert(TABLE_NAME2,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertDoctorsData(String name,String surname,String address,String email,String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_4_1,name);
        contentValues.put(COL_4_2,surname);
        contentValues.put(COL_4_3,address);
        contentValues.put(COL_4_4,email);
        contentValues.put(COL_4_5,phone);

        long result = db.insert(TABLE_NAME4,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }



    public Cursor getAllDoctorsData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME4,null);
        return res;
    }
    public void updateDoctorsData(String name,String surname,String address,String email,String phone ,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME4 + " SET " + COL_4_1 + " = '" + name + "' WHERE ID = " + id);
        db.execSQL("UPDATE " + TABLE_NAME4 + " SET " + COL_4_2 + " = '" + surname + "' WHERE ID = " + id);
        db.execSQL("UPDATE " + TABLE_NAME4 + " SET " + COL_4_3 + " = '" + address + "' WHERE ID = " + id);
        db.execSQL("UPDATE " + TABLE_NAME4 + " SET " + COL_4_4 + " = '" + email + "' WHERE ID = " + id);
        db.execSQL("UPDATE " + TABLE_NAME4 + " SET " + COL_4_5 + " = '" + phone + "' WHERE ID = " + id);
    }




    public Cursor getAllPersonalData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME3,null);
        return res;
    }
    public void updatePersonalData(String name,String surname,String address,String hometown,String tk,String email,String phone,String cell ,int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME3 + " SET " + COL_3_1 + " = '" + name + "' WHERE ID = " + id);
        db.execSQL("UPDATE " + TABLE_NAME3 + " SET " + COL_3_2 + " = '" + surname + "' WHERE ID = " + id);
        db.execSQL("UPDATE " + TABLE_NAME3 + " SET " + COL_3_3 + " = '" + address + "' WHERE ID = " + id);
        db.execSQL("UPDATE " + TABLE_NAME3 + " SET " + COL_3_4 + " = '" + hometown + "' WHERE ID = " + id);
        db.execSQL("UPDATE " + TABLE_NAME3 + " SET " + COL_3_5 + " = '" + tk + "' WHERE ID = " + id);
        db.execSQL("UPDATE " + TABLE_NAME3 + " SET " + COL_3_6 + " = '" + email + "' WHERE ID = " + id);
        db.execSQL("UPDATE " + TABLE_NAME3 + " SET " + COL_3_7 + " = '" + phone + "' WHERE ID = " + id);
        db.execSQL("UPDATE " + TABLE_NAME3 + " SET " + COL_3_8 + " = '" + cell + "' WHERE ID = " + id);
    }




    public void updateState(int state ,int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME2 + " SET " + COL_2_2 + " = " + state + " WHERE ID = " + id);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }


    public Cursor getAllTimes() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME2,null);
        return res;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }


    public Integer deleteTime(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME2, "ID = ?",new String[] {id});
    }
}
