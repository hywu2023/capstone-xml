package kr.co.company.medicine;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.IBinder;

import java.util.Calendar;

public class MyService extends Service {
    String CHANNEL_ID = "Medicine";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return Service.START_STICKY;
        }
        return flags;
    }

    public void setChannel() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
//            String descriptionText = getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(descriptionText);
//            notificationManager.createNotificationChannel(channel);
        }
    }

    private void alarm(Calendar cal, Cursor cursor, long calendarTime, long nowTime, long interval, int dayOfWeek) {
        int CurCount = 3;
        int count = 0;
        PendingIntent[] sender = new PendingIntent[CurCount];

        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        final int id = (int) System.currentTimeMillis();
        sender[count] = PendingIntent.getBroadcast(getApplicationContext(), id, intent, PendingIntent.FLAG_MUTABLE);

        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendarTime, sender[count]);

    }
}
