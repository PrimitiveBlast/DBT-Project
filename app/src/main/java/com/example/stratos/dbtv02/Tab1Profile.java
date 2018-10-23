package com.example.stratos.dbtv02;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Stratos on 11/18/2016.
 */

public class Tab1Profile extends Fragment {
    DatabaseHelper myDb;
    ListView list1,list2,list3;

    ImageButton update ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myDb = new DatabaseHelper(getContext());

        View rootView;
        rootView = inflater.inflate(R.layout.tab1_profile, container, false);

        list1 = (ListView)rootView.findViewById(R.id.list1);
        list2 = (ListView)rootView.findViewById(R.id.list2);
        list3 = (ListView)rootView.findViewById(R.id.list3);
        ArrayAdapter<String> adapter1;
        ArrayAdapter<String> adapter2;
        ArrayAdapter<String> adapter3;
        String[] name=new String[2] ;
        String [] location = new String [3];
        String [] contact = new String [3];
        update = (ImageButton) rootView.findViewById(R.id.imageButton10);


        Cursor res = myDb.getAllPersonalData();
        if (res.getCount()== 0){
            Intent intent = new Intent (getContext(),Tab1ProfileUpdate.class);
            startActivity(intent);
        }
        else {
            res.moveToNext();
            name [0] = res.getString(1);
            name[1] =res.getString(2);
            location[0] = res.getString(3);
            location[1] = res.getString(4);
            location[2] = res.getString(5);
            contact[0] = res.getString(6);
            contact[1] = res.getString(7);
            contact[2] = res.getString(8);


            adapter1 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,name);
            adapter2 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,location);
            adapter3 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,contact);
            list1.setAdapter(adapter1);
            list2.setAdapter(adapter2);
            list3.setAdapter(adapter3);
            update.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {
                            final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
                            animation.setDuration(300); // duration - half a second
                            animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate

                            update.startAnimation(animation);
                            Intent intent = new Intent(getContext(), Tab1ProfileUpdate.class);
                            startActivity(intent);
                        }
                    });
        }
        return rootView;

    }

}



