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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Stratos on 11/18/2016.
 */

public class Tab2Doctor extends Fragment {

    DatabaseHelper myDb;
    ListView list;
    ImageButton update ;
    ArrayAdapter<String> adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2_doctor, container, false);

        myDb = new DatabaseHelper(getContext());
        list = (ListView)rootView.findViewById(R.id.list1);
        update = (ImageButton)rootView.findViewById(R.id.updateBtn);
        String [] docInfo = new String[5];


        Cursor res = myDb.getAllDoctorsData();
        if (res.getCount()== 0){
            Intent intent = new Intent (getContext(),Tab2ProfileUpdate.class);
            startActivity(intent);
        }
        else {
            res.moveToNext();
            docInfo[0]=res.getString(1);
            docInfo[1]=res.getString(2);
            docInfo[2]=res.getString(3);
            docInfo[3]=res.getString(4);
            docInfo[4]=res.getString(5);

            adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,docInfo);
            list.setAdapter(adapter);

            update.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {
                            final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
                            animation.setDuration(300); // duration - half a second
                            animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate

                            update.startAnimation(animation);

                            Intent intent = new Intent(getContext(), Tab2ProfileUpdate.class);
                            startActivity(intent);
                        }
                    });
        }






        return rootView;
    }

}
