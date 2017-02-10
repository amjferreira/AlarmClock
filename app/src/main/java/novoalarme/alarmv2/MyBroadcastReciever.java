package novoalarme.alarmv2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class MyBroadcastReciever extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        //Toast.makeText(context,"o alarme funciona",Toast.LENGTH_LONG).show();
        Log.e("teste para o alarme","funcionou");

    }
}
