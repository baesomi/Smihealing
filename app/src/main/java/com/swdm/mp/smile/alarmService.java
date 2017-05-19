package com.swdm.mp.smile;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class alarmService extends Service {
    NotificationManager nm;
    ServiceThread thread;

    int notificationID = 1;
    String mAlarmMessage[] = {"Say cheese~~!",
            "Your smile is beautiful!",
            "The smile is the beginning of love.",
            "You can go a long way with a smile.",
            "Peace begins with a smile.",
            "A smile is a curve that sets everything straight."};

    public alarmService() {
    }

    //initial create
    @Override
    public void onCreate() {
        super.onCreate();
    }

    //background operate
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myAlarmHandler handler = new myAlarmHandler();
        thread = new ServiceThread(handler);
        thread.start();
        return START_STICKY;
    }

    //service destroy
    @Override
    public void onDestroy() {
        thread.stopForever();
        thread = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected void displayNotification() {
    }

    class myAlarmHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {

            int random = (int) (Math.random() * 6);
            // /---PendingIntentto launch activity if the user selects
            // this notification--
            Intent i = new Intent(alarmService.this, AlarmIntent.class);
//            Intent i = new Intent(alarmService.this, MainActivity.class);
            i.putExtra("notificationID", notificationID);
            PendingIntent pendingIntent = PendingIntent.getActivity(alarmService.this, 0, i, 0);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                    .setSmallIcon(R.drawable.favicon)
                    .setContentTitle("SMIHEALING: it's time to smile!")
                    .setContentText(mAlarmMessage[random]);
            mBuilder.setContentIntent(pendingIntent);
            //---100ms delay, vibrate for 250ms, pause for 100 msand
            // then vibrate for 500ms--
            mBuilder.setVibrate(new long[]{100, 250});

            nm.notify(notificationID, mBuilder.build());

            Toast.makeText(alarmService.this, "service IN PROGRESS", Toast.LENGTH_SHORT).show();
        }
    }
}
