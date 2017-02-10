package novoalarme.alarmv2;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    DbHelper dbHelper;
    Button addAlarm,removeBtn;
    ListView listView;
    CustomCursorAdapter myCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DbHelper(this);
        addAlarm=(Button) findViewById(R.id.addAlarmBt);
        removeBtn=(Button) findViewById(R.id.removeBtn);
        listView= (ListView) findViewById(R.id.list_view);
        final Intent intent = new Intent(this,AlarmPicker.class);

        listAllAlarms();
        setListeners();
        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);

            }
        });
        dropAll();

    }


    void setListeners(){


        final Intent intent = new Intent(getApplicationContext(),AlarmPicker.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor itemCursor= (Cursor) myCursorAdapter.getItem(position);
                int idRow=itemCursor.getInt(itemCursor.getColumnIndex(AlarmDataContract.AlarmEntry._ID));
                String nameRow=itemCursor.getString(itemCursor.getColumnIndex(AlarmDataContract.AlarmEntry.COL_NAME));
                int hourRow=itemCursor.getInt(itemCursor.getColumnIndex(AlarmDataContract.AlarmEntry.COL_HOUR));
                int minuteRow=itemCursor.getInt(itemCursor.getColumnIndex(AlarmDataContract.AlarmEntry.COL_MIN));
                int activeRow=itemCursor.getInt(itemCursor.getColumnIndex(AlarmDataContract.AlarmEntry.COL_STATUS));
                intent.putExtra("ID_SELECTED",idRow);
                intent.putExtra("NAME_SELECTED",nameRow);
                intent.putExtra("HOUR_SELECTED",hourRow);
                intent.putExtra("MINUTE_SELECTED",minuteRow);
                intent.putExtra("ACTIVE_SELECTED",activeRow);

                startActivity(intent);

                Toast.makeText(getBaseContext(),"ID do item clicado"+ String.valueOf(idRow),Toast.LENGTH_LONG).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor itemCursor= (Cursor) myCursorAdapter.getItem(position);
                int idRow=itemCursor.getInt(itemCursor.getColumnIndex(AlarmDataContract.AlarmEntry._ID));

                dbHelper.deleteData(idRow);
                listAllAlarms();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listAllAlarms();
    }


    private void dropAll(){
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getBaseContext(),"Não faço nada!!!",Toast.LENGTH_LONG).show();
                //code to completely delete database
                //getBaseContext().deleteDatabase("alarms.db");
                //listAllAlarms();

            }
        });
    }

    private void listAllAlarms() {

        dbHelper = new DbHelper(this);
        Cursor cursor = dbHelper.listAllRows();
        cursor.moveToFirst();
        myCursorAdapter = new CustomCursorAdapter(getBaseContext(), cursor, 0);
        listView.setAdapter(myCursorAdapter);
    }

/*


     --------------------OBSOLETE CODE THAT USED A CUSTOM CLASS CALLED ALARMDATA-------------

     //this cursor.getCount ensures that the array will not take up more space than needed
     //AlarmData[] alarmList = new AlarmData[cursor.getCount()];
     //String from[]={AlarmDataContract.AlarmEntry.COL_2, AlarmDataContract.AlarmEntry.COL_3, AlarmDataContract.AlarmEntry.COL_5};

     while (!cursor.isAfterLast()){

            AlarmData alarmData = new AlarmData();
            switch (cursor.getInt(cursor.getColumnIndex(AlarmDataContract.AlarmEntry.COL_STATUS))){
                case 1: alarmData.setActive(true);
                    break;
                case 0: alarmData.setActive(false);
                    break;
                default:alarmData.setActive(false);
                    break;
            }
            alarmData.setDate(cursor.getString(cursor.getColumnIndex(AlarmDataContract.AlarmEntry.COL_DATE)));
            alarmData.setTime(cursor.getString(cursor.getColumnIndex(AlarmDataContract.AlarmEntry.COL_TIME)));
            alarmData.setTitle(cursor.getString(cursor.getColumnIndex(AlarmDataContract.AlarmEntry.COL_NAME)));
            alarmData.setId(cursor.getInt(cursor.getColumnIndex(AlarmDataContract.AlarmEntry._ID)));
            alarmList[cursor.getPosition()]=alarmData;
            cursor.moveToNext();
        }*/


}






