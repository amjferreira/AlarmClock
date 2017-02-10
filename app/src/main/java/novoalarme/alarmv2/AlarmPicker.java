package novoalarme.alarmv2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class AlarmPicker extends AppCompatActivity {

    TimePicker timePicker;
    Button addAlarm, testDatabase;
    DbHelper dbHelper;
    String dateTime=null;
    Bundle extras;
    EditText alarmNameSet;
    AlarmManager alarmManager;
    Context context;

    int hh,mm;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_picker);
        dbHelper=new DbHelper(this);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        alarmNameSet= (EditText) findViewById(R.id.alarmName);
        addAlarm = (Button) findViewById(R.id.AddAlarmBt_picker);
        testDatabase= (Button) findViewById(R.id.testButton);
        context=this;

        setAlarm();
        printDatabase();
        extras = getIntent().getExtras();
        setPickerValues();
    }

    private void makeAlarm(int id,int hour, int minute){

        final Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR,hour);
        calendar.set(Calendar.MINUTE,minute);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context,MyBroadcastReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmPicker.this,id,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);




    }


    private void setPickerValues(){

        if (extras!=null){

            int minute= extras.getInt("MINUTE_SELECTED");
            int hour= extras.getInt("HOUR_SELECTED");
            alarmNameSet.setText(extras.getString("NAME_SELECTED"));


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.setHour(hour);
                timePicker.setMinute(minute);
            }else{
                timePicker.setCurrentHour(hour);
                timePicker.setCurrentMinute(minute);
            }

            Log.e("tempo passado e:",String.valueOf(hour)+minute);
        }

    }

    private void setAlarm(){

        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int[] pickerTime=getPickerValues();

                if (extras!=null){


                    int alarmUpdate= dbHelper.updateAlarm(extras.getInt("ID_SELECTED"),
                            pickerTime[0],pickerTime[1], String.valueOf(alarmNameSet.getText()),1);

                    if (alarmUpdate==1){
                        Toast.makeText(AlarmPicker.this,"UPDATE SUCCESSFUL",Toast.LENGTH_LONG).show();
                        makeAlarm(extras.getInt("ID_SELECTED"),pickerTime[0],pickerTime[1]);
                    }
                    else Toast.makeText(AlarmPicker.this,"UPDATE FAILED, VALUE RETURNED: "+ alarmUpdate,Toast.LENGTH_LONG).show();

                    makeAlarm(extras.getInt("ID_SELECTED"),pickerTime[0],pickerTime[1]);



                }
                else {
                    long idReturned=dbHelper.insertNew(String.valueOf(alarmNameSet.getText()),pickerTime[0],pickerTime[1],dateTime,1);
                    if (idReturned!=-1){
                        Toast.makeText(AlarmPicker.this,"Time inserted:"+pickerTime[0]+":"+pickerTime[1],Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(AlarmPicker.this,"Failed to insert",Toast.LENGTH_LONG).show();

                    }
                    Log.e("atual id:", String.valueOf(idReturned));
                    //makeAlarm((int) idReturned,pickerTime[0],pickerTime[1]);

                }



            }
        });



    }

    private int[] getPickerValues(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hh=timePicker.getHour();
            mm=timePicker.getMinute();
        }else {
            hh = timePicker.getCurrentHour();
            mm = timePicker.getCurrentMinute();
        }


        return new int[]{hh,mm};

    }




    private void printDatabase(){
        //method for debug purposes, that lists the db content into the console log
        testDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor= dbHelper.listAllRows();
                Log.e("cursor object", DatabaseUtils.dumpCursorToString(cursor));

            }
        });


    }

}
