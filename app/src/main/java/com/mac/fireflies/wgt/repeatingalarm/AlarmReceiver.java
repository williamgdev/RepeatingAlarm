package com.mac.fireflies.wgt.repeatingalarm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by User on 10/2/2016.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {
        MainActivity mainActivity = MainActivity.activity;
        mainActivity.setAlarmText("Alarm say - Wake up!!!");
        mainActivity.RingStart();

        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmService.class.getName());
        startWakefulService(context, intent.setComponent(comp));
        setResultCode(Activity.RESULT_OK);

        Toast.makeText(context, "Alarm ringing", Toast.LENGTH_LONG).show();
    }

//@Override
//public void onReceive(Context context, Intent intent) {
//    // TODO: This method is called when the BroadcastReceiver is receiving
//    // an Intent broadcast.
//    try{
//
//        int code = getResultCode();
//        Intent i = new Intent(context, AlarmService.class);
//        if(code == 2){
//            context.stopService(i);
//            Toast.makeText(context, "Canceled",Toast.LENGTH_SHORT).show();
//        }else {
//            context.startService(i);
//            Toast.makeText(context, "I'm running",Toast.LENGTH_SHORT).show();
//        }
//    }catch (UnsupportedOperationException e){
//        Log.d("Unsoported: ", e.getMessage());
//    }
//}
}
