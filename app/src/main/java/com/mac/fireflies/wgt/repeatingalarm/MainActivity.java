package com.mac.fireflies.wgt.repeatingalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    TimePicker timePicker;
    static MainActivity activity;
    TextView textView;
    Ringtone ringtone;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private ToggleButton toggleButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        textView = (TextView) findViewById(R.id.alarmText);
        toggleButton = (ToggleButton) findViewById(R.id.alarmToggle);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if(!toggleButton.isChecked()){
                    Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);//the same as up
                    intent.setAction("AlarmStart");//the same as up
                    if (pendingIntent == null)
                        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 1001, intent, PendingIntent.FLAG_CANCEL_CURRENT);//the same as up

                    alarmManager.cancel(pendingIntent);//important
                    pendingIntent.cancel();//important
                    RingStop();
                    toggleButton.setChecked(true);
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        activity = this;
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onToggleClicked(View view) {
        if (!((ToggleButton) view).isChecked()) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            calendar.set(Calendar.MINUTE, timePicker.getMinute());
            calendar.set(Calendar.MILLISECOND, 0);
            Intent intent = new Intent(this, AlarmReceiver.class);
            intent.setAction("AlarmStart");
            pendingIntent = PendingIntent.getBroadcast(this, 1001, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            //alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

            toggleButton.setChecked(false);

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    0, pendingIntent);
            String s = "";



//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                    0 , pendingIntent);


//            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                    3000,
//                    3000, pendingIntent);


        } else {

//            alarmManager.cancel(pendingIntent);
//            setAlarmText("");

             Intent intent = new Intent(this, AlarmReceiver.class);//the same as up
            intent.setAction("AlarmStart");//the same as up
            if (pendingIntent == null)
                pendingIntent = PendingIntent.getBroadcast(this, 1001, intent, PendingIntent.FLAG_CANCEL_CURRENT);//the same as up

            alarmManager.cancel(pendingIntent);//important
            pendingIntent.cancel();//important
            RingStop();
            toggleButton.setChecked(true);


        }
    }

    private void checkAlarmisCreated(Intent intent) {
        boolean alarmUp = (PendingIntent.getBroadcast(this, 1001,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT) != null);

        if (alarmUp)
        {
            Toast.makeText(this, "Alarm is already active at ", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "There is no alarm", Toast.LENGTH_LONG).show();
    }

    public void setAlarmText(String alarmText) {
        textView.setText(alarmText);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
    public void RingStart(){

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (uri == null)
            uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        ringtone = RingtoneManager.getRingtone(this, uri);
        ringtone.play();
    }
    public void RingStop(){
        if (ringtone != null)
            ringtone.stop();
    }

    public void CheckAlarm(View view) {
        checkAlarmisCreated(this.getIntent());
    }

}
