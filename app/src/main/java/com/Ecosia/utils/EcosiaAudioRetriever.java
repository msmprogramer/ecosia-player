package com.Ecosia.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.Ecosia.model.AudioFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class EcosiaAudioRetriever {

    private static final String TAG = "EcosiaAudioRetriever";

    public static EcosiaAudioRetriever newInstance() {
        return new EcosiaAudioRetriever();
    }

    private List<AudioFile> getAllAudioFiles(Context context) {

        List<AudioFile> audioFiles = new ArrayList<>();

        ContentResolver contentResolver = context.getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = contentResolver.query(uri, null, selection, null, sortOrder);
        int count = 0;

        if(cursor != null)
        {
            count = cursor.getCount();

            Log.d(TAG, "getAllAudioFiles: "+ count);

            if(count > 0)
            {
                while(cursor.moveToNext())
                {
                    String artist = cursor.getString(
                            cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
                    );
                    String title = cursor.getString(
                            cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
                    );
                    String path = cursor.getString(
                            cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
                    );

                    Log.d(TAG, "getAllAudioFiles: "+artist +" : "+ title + " : " + path);

                    audioFiles.add(new AudioFile(artist, title, path));
                }

            }
        }

        if (cursor != null) {
            cursor.close();
        }

        return audioFiles;
    }

    public  AudioFile getRandomAudioFile(Context  context) {
        List<AudioFile> audiosFile = getAllAudioFiles(context);
        if (audiosFile.isEmpty()) {
            return null;
        }
        return audiosFile.get(new Random().nextInt(audiosFile.size()));
    }
}
