package com.example.stratos.dbtv02;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

/**
 * Created by Stratos on 11/22/2016.
 */

public class Tab2ProfileUpdate extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText name;
    EditText surname;
    EditText address;
    EditText email;
    EditText phone;
    Button save;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab2_doctor_update);
        myDb = new DatabaseHelper(this);
        name = (EditText) findViewById(R.id.editName);
        surname = (EditText) findViewById(R.id.editSurname);
        address = (EditText) findViewById(R.id.editAddress);
        email = (EditText) findViewById(R.id.editMail);
        phone = (EditText) findViewById(R.id.editPhone);
        save = (Button) findViewById(R.id.displayBtn);

        Toast.makeText(Tab2ProfileUpdate.this, "Insert your doctors Data", Toast.LENGTH_SHORT).show();

        Cursor res = myDb.getAllDoctorsData();
        if(res.getCount() > 0) {
            res.moveToNext();
            id = res.getInt(0);
            name.setText(res.getString(1));
            surname.setText(res.getString(2));
            address.setText(res.getString(3));
            email.setText(res.getString(4));
            phone.setText(res.getString(5));


            save.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {
                            final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
                            animation.setDuration(300); // duration - half a second
                            animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate

                            save.startAnimation(animation);
                            if (checkEntries(name.getText().toString(),
                                    surname.getText().toString(),
                                    address.getText().toString(),
                                    email.getText().toString(),
                                    phone.getText().toString())){

                                myDb.updateDoctorsData(
                                        name.getText().toString().substring(0,1).toUpperCase()+ name.getText().toString().substring(1),
                                        surname.getText().toString().substring(0,1).toUpperCase()+ surname.getText().toString().substring(1),
                                        address.getText().toString().substring(0,1).toUpperCase()+ address.getText().toString().substring(1),
                                        email.getText().toString(),
                                        phone.getText().toString(),id);

                                Intent intent = new Intent(Tab2ProfileUpdate.this, MedBook.class);
                                startActivity(intent);
                            }
                        }

                    });
        }
        else{
            res.moveToNext();

            save.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {
                            final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
                            animation.setDuration(300); // duration - half a second
                            animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate

                            save.startAnimation(animation);
                            if (checkEntries(name.getText().toString(),
                                    surname.getText().toString(),
                                    address.getText().toString(),
                                    email.getText().toString(),
                                    phone.getText().toString())){

                                myDb.insertDoctorsData(
                                        name.getText().toString().substring(0,1).toUpperCase()+ name.getText().toString().substring(1),
                                        surname.getText().toString().substring(0,1).toUpperCase()+ surname.getText().toString().substring(1),
                                        address.getText().toString().substring(0,1).toUpperCase()+ address.getText().toString().substring(1),
                                        email.getText().toString(),
                                        phone.getText().toString());

                                Intent intent = new Intent(Tab2ProfileUpdate.this, MedBook.class);
                                startActivity(intent);
                            }
                        }

                    });

        }
    }

    public boolean checkEntries(String name, String surname, String address, String email, String phone){
        if (name.isEmpty() || surname.isEmpty() || address.isEmpty() ||  email.isEmpty() || phone.isEmpty()){
            Toast.makeText(Tab2ProfileUpdate.this,"All fields should be filled",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (name.matches(".*\\d+.*") || surname.matches(".*\\d+.*")){
            Toast.makeText(Tab2ProfileUpdate.this,"Name and surname have to contain only characters",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!phone.isEmpty()) {
            if (Pattern.matches("[a-zA-Z]+", phone) || phone.length() != 10) {
                Toast.makeText(Tab2ProfileUpdate.this, "Phone number should contain 10 digits", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (!Pattern.matches("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$", email)){
            Toast.makeText(Tab2ProfileUpdate.this,"Invalid E-mail",Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }



}



