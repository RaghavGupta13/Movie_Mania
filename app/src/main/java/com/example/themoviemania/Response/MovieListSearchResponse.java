package com.example.themoviemania.Response;

import com.example.themoviemania.Models.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//This Class if for getting list of movies.
public class MovieListSearchResponse {

    @SerializedName("results")
    @Expose()
    private List<Movie> movies;

    @SerializedName("total_pages")
    @Expose()
    private int pages;

    public List<Movie> getMovies(){
        return movies;
    }

    public int getPages(){
        return pages;
    }

    @Override
    public String toString() {
        return "MovieListSearchResponse{" +
                "movies=" + movies +
                ", pages=" + pages +
                '}';
    }
}
