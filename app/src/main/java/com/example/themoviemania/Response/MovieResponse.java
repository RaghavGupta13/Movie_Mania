package com.example.themoviemania.Response;

import com.example.themoviemania.Models.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//The response will be for a single movie

public class MovieResponse {

    @SerializedName("results")
    @Expose()
    private Movie movie;

    public Movie getMovie(){
        return movie;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movie=" + movie +
                '}';
    }
}
