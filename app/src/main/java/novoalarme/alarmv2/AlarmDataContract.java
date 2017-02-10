package novoalarme.alarmv2;

import android.provider.BaseColumns;



 final class AlarmDataContract {

     //set to private in order to prevent the constructor being called by accident
    private AlarmDataContract(){

    }

    static class AlarmEntry{
        static final String TABLE_NAME="alarms";
        static final String _ID="_id";
        static final String COL_NAME="name";
        static final String COL_HOUR="hour";
        static final String COL_MIN = "minute";
        //static final String COL_TIME="time_set";
        static final String COL_DATE="date_set";
        static final String COL_STATUS="status";
        static final String[] ALL_COLS={_ID,COL_NAME,COL_HOUR,COL_MIN,COL_DATE,COL_STATUS};

    }

}
