package novoalarme.alarmv2;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;



//Cursor adapter pronto a utilizar
//passar para a implementação



public class CustomCursorAdapter extends CursorAdapter {

    private LayoutInflater cursorInflater;


    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        cursorInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.alarm_row,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView timeDisplay= (TextView) view.findViewById(R.id.timeDefined);
        TextView titleDisplay=(TextView) view.findViewById(R.id.titleDefined);
        Switch switchDisplay=(Switch) view.findViewById(R.id.switchDefined);
        int hour = cursor.getInt(cursor.getColumnIndex(AlarmDataContract.AlarmEntry.COL_HOUR));
        int minute=cursor.getInt(cursor.getColumnIndex(AlarmDataContract.AlarmEntry.COL_MIN));
        String title=cursor.getString(cursor.getColumnIndex(AlarmDataContract.AlarmEntry.COL_NAME));
        int status =cursor.getInt(cursor.getColumnIndex(AlarmDataContract.AlarmEntry.COL_STATUS));



        timeDisplay.setText(displayTime(hour,minute));
        titleDisplay.setText(title);
        if (status==1)
            switchDisplay.setChecked(true);
        else switchDisplay.setChecked(false);
    }


    private String displayTime(int hh, int mm){

        if (mm<10) return hh+":0"+mm;
        else return hh+":"+mm;


    }
}
