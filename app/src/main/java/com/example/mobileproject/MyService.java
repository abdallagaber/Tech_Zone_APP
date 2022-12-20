package com.example.mobileproject;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {
    private MediaPlayer player;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "audio started", Toast.LENGTH_SHORT).show();
        player = MediaPlayer.create(this, R.raw.app_idea1);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.setLooping(false);
        player.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "audio stopped" , Toast.LENGTH_SHORT).show();
        player.stop();
    }
}