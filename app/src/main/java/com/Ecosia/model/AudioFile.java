package com.Ecosia.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohamed Salama on 8/16/2016.
 */
public class AudioFile{

    private String artist;
    private String title;
    private String path;

    public AudioFile(String artist, String title, String path) {
        this.artist = artist;
        this.title = title;
        this.path = path;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

}