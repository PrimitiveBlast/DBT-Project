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
 * Created by Stratos on 11/21/2016.
 */

public class Tab1ProfileUpdate extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText name;
    EditText surname;
    EditText address;
    EditText hometown;
    EditText tk;
    EditText email;
    EditText phone;
    EditText cell;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1_profile_update);
        myDb = new DatabaseHelper(this);

        Toast.makeText(Tab1ProfileUpdate.this, "Insert your personal Data", Toast.LENGTH_SHORT).show();

        name = (EditText) findViewById(R.id.editName);
        surname = (EditText) findViewById(R.id.editSurname);
        address = (EditText) findViewById(R.id.editAddress);
        hometown = (EditText) findViewById(R.id.editCity);
        tk = (EditText) findViewById(R.id.editPostal);
        email = (EditText) findViewById(R.id.editMail);
        phone = (EditText) findViewById(R.id.editPhone);
        cell = (EditText) findViewById(R.id.editMobile);
        save = (Button) findViewById(R.id.displayBtn);

        Cursor res = myDb.getAllPersonalData();
        if(res.getCount() > 0) {
            res.moveToNext();
            name.setText(res.getString(1));
            surname.setText(res.getString(2));
            address.setText(res.getString(3));
            hometown.setText(res.getString(4));
            tk.setText(res.getString(5));
            email.setText(res.getString(6));
            phone.setText(res.getString(7));
            cell.setText(res.getString(8));

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
                                hometown.getText().toString(),
                                tk.getText().toString(),
                                email.getText().toString(),
                                phone.getText().toString(),
                                cell.getText().toString())){

                                    myDb.updatePersonalData(
                                            name.getText().toString().substring(0,1).toUpperCase()+ name.getText().toString().substring(1),
                                            surname.getText().toString().substring(0,1).toUpperCase()+ surname.getText().toString().substring(1),
                                            address.getText().toString().substring(0,1).toUpperCase()+ address.getText().toString().substring(1),
                                            hometown.getText().toString().substring(0,1).toUpperCase()+ hometown.getText().toString().substring(1),
                                            tk.getText().toString(),
                                            email.getText().toString(),
                                            phone.getText().toString(),
                                            cell.getText().toString(),1);

                                    Intent intent = new Intent(Tab1ProfileUpdate.this, MedBook.class);
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
                                    hometown.getText().toString(),
                                    tk.getText().toString(),
                                    email.getText().toString(),
                                    phone.getText().toString(),
                                    cell.getText().toString())){

                                myDb.insertPersonalData(
                                        name.getText().toString().substring(0,1).toUpperCase()+ name.getText().toString().substring(1),
                                        surname.getText().toString().substring(0,1).toUpperCase()+ surname.getText().toString().substring(1),
                                        address.getText().toString().substring(0,1).toUpperCase()+ address.getText().toString().substring(1),
                                        hometown.getText().toString().substring(0,1).toUpperCase()+ hometown.getText().toString().substring(1),
                                        tk.getText().toString(),
                                        email.getText().toString(),
                                        phone.getText().toString(),
                                        cell.getText().toString());

                                Intent intent = new Intent(Tab1ProfileUpdate.this, MedBook.class);
                                startActivity(intent);
                            }
                        }

                    });

        }
    }

    public boolean checkEntries(String name, String surname, String address, String hometown, String tk, String email, String phone, String cell){
        if (name.isEmpty() || surname.isEmpty() || address.isEmpty() || hometown.isEmpty() || tk.isEmpty() || email.isEmpty() || cell.isEmpty()){
            Toast.makeText(Tab1ProfileUpdate.this,"All fields should be filled",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (name.matches(".*\\d+.*") || surname.matches(".*\\d+.*")){
            Toast.makeText(Tab1ProfileUpdate.this,"Name and surname have to contain only characters",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Pattern.matches("[a-zA-Z]+", tk) || tk.length() != 5){
            Toast.makeText(Tab1ProfileUpdate.this,"Postal Code should contain 5 digits",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!phone.isEmpty()) {
            if (Pattern.matches("[a-zA-Z]+", phone) || phone.length() != 10) {
                Toast.makeText(Tab1ProfileUpdate.this, "Phone number should contain 10 digits", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (Pattern.matches("[a-zA-Z]+", cell) || cell.length() != 10) {
            Toast.makeText(Tab1ProfileUpdate.this, "Cell phone number should contain 10 digits", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Pattern.matches("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$", email)){
            Toast.makeText(Tab1ProfileUpdate.this,"Invalid E-mail",Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    @Override
    public void onBackPressed()
    {

       Intent intent = new Intent(Tab1ProfileUpdate.this,MainActivity.class);
        startActivity(intent);
    }
}
