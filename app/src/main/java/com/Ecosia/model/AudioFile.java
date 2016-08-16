package com.Ecosia.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohamed Salama on 8/16/2016.
 */
public class AudioFile implements Parcelable {

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

    protected AudioFile(Parcel in) {
        artist = in.readString();
        title = in.readString();
        path = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(artist);
        dest.writeString(title);
        dest.writeString(path);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<AudioFile> CREATOR = new Parcelable.Creator<AudioFile>() {
        @Override
        public AudioFile createFromParcel(Parcel in) {
            return new AudioFile(in);
        }

        @Override
        public AudioFile[] newArray(int size) {
            return new AudioFile[size];
        }
    };
}