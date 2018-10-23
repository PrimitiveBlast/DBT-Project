package com.example.stratos.dbtv02;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.Toast;

import static android.net.sip.SipErrorCode.TIME_OUT;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x*9/10;
        int height = size.y*9/10;

        final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        animation.setDuration(500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate


        final ImageButton buttons[] = new ImageButton[6];

        buttons[0] = (ImageButton) findViewById(R.id.imageButton1);
        buttons[1] = (ImageButton) findViewById(R.id.imageButton2);
        buttons[2] = (ImageButton) findViewById(R.id.imageButton3);
        buttons[3] = (ImageButton) findViewById(R.id.imageButton4);
        buttons[4] = (ImageButton) findViewById(R.id.imageButton5);
        buttons[5] = (ImageButton) findViewById(R.id.imageButton6);


        for(int i=0; i<6; i++)
        {
            buttons[i].getLayoutParams().height = height / 3 ;//set appropriate sizes
            buttons[i].getLayoutParams().width= width / 2 ;
            buttons[i].requestLayout();//this line redraws the imageview again call only after you set the size
            buttons[i].setScaleType(ImageButton.ScaleType.CENTER_INSIDE);

            if (i==0)
            {
                buttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    buttons[0].startAnimation(animation);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(MainActivity.this, NewMeasurement.class);
                            startActivity(i);
                        }
                    }, 400);


                    }
                });
            }
            if (i==1)
            {
                buttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    buttons[1].startAnimation(animation);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(MainActivity.this, MCalendarActivity.class);
                            startActivity(i);
                        }
                    }, 400);


                    }
                });
            }
            if (i==2)
            {
                buttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        buttons[2].startAnimation(animation);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(MainActivity.this, Measurment_List.class);
                                startActivity(i);
                            }
                        }, 400);


                    }
                });
            }

            if (i==3) {
                buttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        buttons[3].startAnimation(animation);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(MainActivity.this, MedBook.class);
                                startActivity(i);
                            }
                        }, 400);


                    }
                });
            }
            if (i==4)
            {
                buttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        buttons[4].startAnimation(animation);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(MainActivity.this, Stats.class);
                                startActivity(i);
                            }
                        }, 400);


                    }
                });
            }
            if (i==5)
            {
                buttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        buttons[5].startAnimation(animation);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(MainActivity.this, Reminder.class);
                                startActivity(i);
                            }
                        }, 400);


                    }
                });
            }
        }


    }
}
