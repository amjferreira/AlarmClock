package novoalarme.alarmv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



class DbHelper extends SQLiteOpenHelper {

    // Variables that will define the usage of the database
    private static final String DB_NAME="alarms.db";
    private static final int DB_VERSION=2;

    private static final String SQL_CREATE_TABLE="CREATE TABLE " + AlarmDataContract.AlarmEntry.TABLE_NAME + " ("+
            AlarmDataContract.AlarmEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            AlarmDataContract.AlarmEntry.COL_NAME+" TEXT, " +
            AlarmDataContract.AlarmEntry.COL_HOUR+" INTEGER NOT NULL, "+
            AlarmDataContract.AlarmEntry.COL_MIN+" INTEGER NOT NULL, "+
            AlarmDataContract.AlarmEntry.COL_DATE+" TEXT, " +
            AlarmDataContract.AlarmEntry.COL_STATUS+" INTEGER NOT NULL)";

    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + AlarmDataContract.AlarmEntry.TABLE_NAME;


    //End of The Variables


    protected void dropAll(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AlarmDataContract.AlarmEntry.TABLE_NAME,null,null);

    }


    //constructor
    DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);

    }

    int updateAlarm(int id,int hh,int mm,String name,int isActive){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AlarmDataContract.AlarmEntry.COL_NAME,name);
        contentValues.put(AlarmDataContract.AlarmEntry._ID,id);
        contentValues.put(AlarmDataContract.AlarmEntry.COL_HOUR,hh);
        contentValues.put(AlarmDataContract.AlarmEntry.COL_MIN,mm);
        contentValues.put(AlarmDataContract.AlarmEntry.COL_STATUS,isActive);
        return db.update(AlarmDataContract.AlarmEntry.TABLE_NAME,contentValues,
                AlarmDataContract.AlarmEntry._ID+"=?",new String[]{String.valueOf(id)});



    }

    long insertNew(String name, int hh, int mm, String date, int status){

        //Inicialize de Database
        SQLiteDatabase db= this.getWritableDatabase();

        //Contains the set of data do be added
        ContentValues contentValues = new ContentValues();

        contentValues.put(AlarmDataContract.AlarmEntry.COL_NAME,name);
        contentValues.put(AlarmDataContract.AlarmEntry.COL_HOUR,hh);
        contentValues.put(AlarmDataContract.AlarmEntry.COL_MIN,mm);
        contentValues.put(AlarmDataContract.AlarmEntry.COL_STATUS,status);

        //o metodo insert devolve -1 caso haja um erro na inserção ou o valor da linha submetida caso haja sucesso
        //por esta razão é atribuido a uma var do tipo long, para saber se houve sucesso ou não

        //return vai ser verdadeiro caso resultado seja diferente de -1 (não houver erro)

        return db.insert(AlarmDataContract.AlarmEntry.TABLE_NAME,null,contentValues);



    }

    public Cursor listSingleRow(long id){

        SQLiteDatabase db= this.getReadableDatabase();

        // The string projection will return the fields I want, its good usage in order to prevent returning more than needed.
        String[] projection={
                AlarmDataContract.AlarmEntry._ID,
                AlarmDataContract.AlarmEntry.COL_NAME,
                AlarmDataContract.AlarmEntry.COL_HOUR,
                AlarmDataContract.AlarmEntry.COL_MIN,
                AlarmDataContract.AlarmEntry.COL_STATUS
            };

        String selection= AlarmDataContract.AlarmEntry._ID +"=?";

        String args[]={String.valueOf(id)};

        return db.query(AlarmDataContract.AlarmEntry.TABLE_NAME,
                projection, //in order to list all rows, we can create a String Array, that contains all, in order to allow re-usage
                            //in this exercise I've created an ALL_COLS variable for that purpose
                selection,
                args,
                null,
                null,
                null); //it is possible to create a sorting order
        //Example: String sortOrder =AlarmDataContract.AlarmEntry.COL_2 + " DESC";

    }

    Cursor listAllRows(){

        SQLiteDatabase db= this.getReadableDatabase();
        String sortOrder =AlarmDataContract.AlarmEntry.COL_HOUR + " ASC, "+ AlarmDataContract.AlarmEntry.COL_MIN;
        return db.query(AlarmDataContract.AlarmEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                sortOrder,
                null);


        //return cursor;
    }

    int deleteData(int id){
        SQLiteDatabase db= getWritableDatabase();

        String where= AlarmDataContract.AlarmEntry._ID+"= ?";
        String[] args={String.valueOf(id)};

        return db.delete(AlarmDataContract.AlarmEntry.TABLE_NAME,where,args);

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }



}
