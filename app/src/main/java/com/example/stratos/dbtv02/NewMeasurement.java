package com.example.stratos.dbtv02;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


public class NewMeasurement extends AppCompatActivity {

    private final int REQUEST_ENABLE_BT = 1;
    private UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothSocket btSocket = null;
    TextView txtArduino;
    Handler mHandler;
    private StringBuilder sb = new StringBuilder();
    final int RECIEVE_MESSAGE = 1;
    private ConnectedThread mConnectedThread;
    private static String address = "98:D3:32:30:5A:FA";
    private static final String TAG = "bluetooth developers";
    DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_new_measurement);
        myDb = new DatabaseHelper(this);
//        //========================================================================================================
        final Button button = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.displayBtn);
        final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        animation.setDuration(300); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate

        checkBt();

        txtArduino = (TextView) findViewById(R.id.txtArduino);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/DIGITALDREAM.ttf");
        txtArduino.setTypeface(custom_font);

        AddData();




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button.startAnimation(animation);

                Log.d(TAG, "...onResume - try connect...");

                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);

                try {
                    btSocket = createBluetoothSocket(device);
                } catch (IOException e) {
                    errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
                }

                Log.d(TAG, "...Connecting...");
                try {
                    btSocket.connect();
                    Log.d(TAG, "....Connection ok...");
                } catch (IOException e) {
                    try {
                        btSocket.close();
                    } catch (IOException e2) {
                        errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
                    }
                }

                Log.d(TAG, "...Create Socket...");

                mConnectedThread = new ConnectedThread(btSocket);
                mConnectedThread.start();
            }
        });



        mHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case RECIEVE_MESSAGE:                                                    // if receive massage
                        byte[] readBuf = (byte[]) msg.obj;
                        String strIncom = new String(readBuf, 0, msg.arg1);                    // create string from bytes array
                        sb.append(strIncom);                                                // append string
                        int endOfLineIndex = sb.indexOf("\r\n");                            // determine the end-of-line
                        if (endOfLineIndex > 0) {                                            // if end-of-line,
                            String sbprint = sb.substring(0, endOfLineIndex);                // extract string
                            sb.delete(0, sb.length());                                        // and clear
                            txtArduino.setText(sbprint);            // update TextView

                        }
                        Log.d(TAG, "...String:"+ sb.toString() +  "Byte:" + msg.arg1 + "...");
                        break;
                }
            }

            ;


        };
    }

    public  void AddData() {
        final Button button2 = (Button) findViewById(R.id.displayBtn);

        button2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
                        animation.setDuration(300); // duration - half a second
                        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate

                        button2.startAnimation(animation);

                        final String[] meals = new String[]{
                                "Breakfast",
                                "Lunch",
                                "Dinner",
                                "Sleep"
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(NewMeasurement.this);
                        builder.setTitle("Select type of meal")
                                .setSingleChoiceItems(meals, 0,
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                // TODO Auto-generated method stub
                                                String selected = meals[arg1];
                                            }
                                        })
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {

                                        ListView lw = ((AlertDialog)dialog).getListView();
                                        Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                                        boolean isInserted = myDb.insertData(Integer.valueOf(txtArduino.getText().toString()), checkedItem.toString());
                                        if(isInserted){
                                            Toast.makeText(NewMeasurement.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                            Toast.makeText(NewMeasurement.this,"Data not Inserted",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder.show();
                    }
                }
        );
    }



    public void checkBt(){
        if (mBluetoothAdapter == null) {
            Toast.makeText(getBaseContext(), "BT Not Supported", Toast.LENGTH_LONG).show();
            finish();
        }
        else if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        if (Build.VERSION.SDK_INT >= 10) {
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[]{UUID.class});
                return (BluetoothSocket) m.invoke(device, MY_UUID);
            } catch (Exception e) {
                Log.e(TAG, "Could not create Insecure RFComm Connection", e);
            }
        }
        return device.createRfcommSocketToServiceRecord(MY_UUID);
    }

    private class ConnectedThread extends Thread {
        BluetoothSocket mmSocket;
        BluetoothDevice mmDevice;
        private InputStream mmInStream;
        private OutputStream mmOutStream;

        public ConnectedThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
            }
            mmSocket = tmp;
        }

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];  // buffer store for the stream
            int bytes; // bytes returned from read()

            while (true) {
                try {
                    // Read from the InputStream

                    bytes = mmInStream.read(buffer);        // Get number of bytes and message in "buffer"
                    mHandler.obtainMessage(RECIEVE_MESSAGE, bytes, -1, buffer).sendToTarget();        // Send to message queue Handler
                } catch (IOException e) {
                    break;
                }
            }
        }
    }

    private void errorExit(String title, String message) {
        Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
        finish();
    }
}

