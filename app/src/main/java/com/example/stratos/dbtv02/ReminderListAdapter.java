package com.example.stratos.dbtv02;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class ReminderListAdapter extends ArrayAdapter<String> {

    customSwitchListener customListener;

    public interface customSwitchListener {
        public void onSwitchClickListener(int position, boolean state);
    }

    public void setCustomSwitchListener(customSwitchListener listener) {
        this.customListener = listener;
    }

    private final Activity context;
    private final String[] hour;
    private final String[] state;

    public ReminderListAdapter(Activity context,String [] hour, String[] state) {
        super(context, R.layout.reminder_layout, hour);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.hour=hour;
        this.state=state;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder() ;
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.reminder_layout, null,true);


        TextView txtHour = (TextView) rowView.findViewById(R.id.txtHour);
        viewHolder.onoff_switch= (Switch) rowView.findViewById(R.id.switch1);
        if (state[position].equals("0")) {
            viewHolder.onoff_switch.setChecked(false);
        }
        else{
            viewHolder.onoff_switch.setChecked(true);
        }


        txtHour.setText(hour[position]);

        viewHolder.onoff_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (customListener != null) {
                    customListener.onSwitchClickListener(position, isChecked);
                }


            }
        });

        return rowView;

    };

    public class ViewHolder {
        Switch onoff_switch;
    }

}
