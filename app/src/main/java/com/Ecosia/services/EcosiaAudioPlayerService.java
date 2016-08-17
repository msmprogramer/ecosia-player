package com.Ecosia.services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.Ecosia.R;
import com.Ecosia.model.AudioFile;
import com.Ecosia.ui.EcosiaPlayerWidget;
import com.Ecosia.utils.EcosiaAudioRetriever;

import java.io.IOException;

public class EcosiaAudioPlayerService extends Service implements
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener {

    private static final String TAG = "EcosiaAudioPlayerService";
    
    public static final String ACTION_PLAY = "ccom.Ecosia.services.EcosiaAudioPlayerService.action.PLAY";
    public static final String ACTION_STOP = "com.Ecosia.services.EcosiaAudioPlayerService.action.STOP";
    public static final String EXTRA_MEDIA_PATH = "mediaPathExtra";

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        initMediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action.equals(ACTION_PLAY)) {
            AudioFile audioFile =
                    EcosiaAudioRetriever.
                            newInstance().
                            getRandomAudioFile(getApplicationContext());

            if (audioFile == null) {
                Toast.makeText(
                        getApplicationContext(),
                        getApplicationContext().getString(R.string.text_no_files_to_play),
                        Toast.LENGTH_LONG
                ).show();

                return 0;
            }

            playAudio(audioFile);
        } else if (action.equals(ACTION_STOP)) {
            stopAudio();
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        stopAudio();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    private void initMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
        }
    }

    private void playAudio(AudioFile audioFile) {
        if (mediaPlayer == null) {
            initMediaPlayer();
        } else {
            mediaPlayer.reset();
        }

        try {
            mediaPlayer.setDataSource(audioFile.getPath());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        updateEcosiaAudioPlayerWidget(getApplicationContext(), audioFile);
    }

    private void stopAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            releaseMediaPlayer();
        }

    }

    private void releaseMediaPlayer() {
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
        stopSelf();
    }

    private void updateEcosiaAudioPlayerWidget(Context context, AudioFile audioFile) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_ecosia_player);

        remoteViews.setTextViewText(R.id.artistTextView, audioFile.getArtist());
        remoteViews.setTextViewText(R.id.titleTextView, audioFile.getTitle());

        EcosiaPlayerWidget.pushWidgetUpdate(context.getApplicationContext(),
                remoteViews);
    }
}
