package com.example.musicplayerassignment;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import java.io.File;

import static com.example.musicplayerassignment.App.CHANNEL_ID;

public class myService extends Service {
    MediaPlayer mediaPlayer;
    private NotificationManagerCompat myNotification;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();
        myNotification = NotificationManagerCompat.from(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(myService.this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(myService.this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(myService.this, CHANNEL_ID)
                .setContentTitle("Music is running")
                .setSmallIcon(R.drawable.ic_audiotrack_black_24dp)
                .setContentIntent(pendingIntent)
                .build();
        myNotification.notify(1, notification);
        startForeground(1,notification);

        String voice = Environment.getExternalStorageDirectory().getPath() + "/Download/mysong.mp3";
        Log.d("MAIN",voice);
        File file = new File(voice);
        Log.d("Main", " voice exists : " + file.exists() + ", can read : " + file.canRead());
         mediaPlayer = MediaPlayer.create(this, Uri.parse("file://" + voice));

        if (mediaPlayer==null){
            Toast.makeText(this,"media player is null",Toast.LENGTH_LONG).show();
        }
        else
            mediaPlayer.start();


        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();

            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }

        super.onDestroy();
    }



}
