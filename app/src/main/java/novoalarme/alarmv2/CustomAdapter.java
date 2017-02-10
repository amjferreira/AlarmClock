package novoalarme.alarmv2;


import android.content.Context;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

class CustomAdapter extends ArrayAdapter<AlarmData> {

    public CustomAdapter(Context context, AlarmData[] resource) {

        super(context, R.layout.alarm_row,resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.alarm_row,parent,false);

        //The class AlarmData is going to contain the information needed.
        //there are properties for time, date, title and whether or not it is active

        AlarmData dataSet = getItem(position);

        //Log.d("O meu item",dataSet.getTitle());
        TextView timeDefined= (TextView) view.findViewById(R.id.timeDefined);
        TextView titleDefined=(TextView) view.findViewById(R.id.titleDefined);
        Switch switchDefined= (Switch) view.findViewById(R.id.switchDefined);

        assert dataSet != null; //assert grants that the the given state is true, in this case, it maks sure we are not getting an empty entry
        timeDefined.setText(dataSet.getTime());
        titleDefined.setText(dataSet.getTitle());
        switchDefined.setChecked(dataSet.isActive());

        return view;

    }
}
