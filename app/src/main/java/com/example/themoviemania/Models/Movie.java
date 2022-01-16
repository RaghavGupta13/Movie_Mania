package com.example.themoviemania.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {

     private int id;
     private String title;

     @SerializedName("overview")
     private String description;

     @SerializedName("poster_path")
     private String poster_image_path;

     private float vote_average;
     private String release_date;

     @SerializedName("")
     private int runtime;

    public Movie(int id, String title, String description, String poster_image_path, float vote_average, String release_date, int runtime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.poster_image_path = poster_image_path;
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.runtime = runtime;
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        poster_image_path = in.readString();
        vote_average = in.readFloat();
        release_date = in.readString();
        runtime = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPoster_image_path() {
        return poster_image_path;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getRuntime() {
        return runtime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(poster_image_path);
        parcel.writeFloat(vote_average);
        parcel.writeString(release_date);
        parcel.writeInt(runtime);
    }
}
