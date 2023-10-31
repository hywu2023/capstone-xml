package kr.co.company.medicine;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    String CHANNEL_ID = "NowMed";

    @Override
    public void onReceive(Context context, Intent intent) {
        int req = intent.getIntExtra("req", 0);

//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_IMMUTABLE);
        Log.d("testing2", String.valueOf(pendingIntent));

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Log.d("testing3", String.valueOf(context));

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.medicine_img).setContentTitle("NowMed").setContentText("약 먹을 시간입니다.")
                .setWhen(Calendar.getInstance().getTimeInMillis()).setContentIntent(pendingIntent).setAutoCancel(true);

        notificationManager.notify(0, builder.build());
    }
}



