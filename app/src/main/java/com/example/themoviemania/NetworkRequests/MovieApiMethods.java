package com.example.themoviemania.NetworkRequests;

import com.example.themoviemania.Models.Movie;
import com.example.themoviemania.Response.MovieListSearchResponse;
import com.example.themoviemania.Response.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiMethods {
    //https://api.themoviedb.org/3/search/movie?api_key=52a18783ed514602a5facb15a0177e61&query=padmaavat

    @GET("search/movie")
    Call<MovieListSearchResponse> getMovies(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

    //https://api.themoviedb.org/3/movie/550?api_key=52a18783ed514602a5facb15a0177e61
//    @GET("movie/{id}?")
//    Call<Movie> getMovie(
//            @Path("id") int id,
//            @Query("api_key") String key
//    );

    //https://api.themoviedb.org/3/movie/popular?api_key=52a18783ed514602a5facb15a0177e61&page=1
    @GET("movie/popular?")
    Call<MovieListSearchResponse> getPopularMovies(
            @Query("api_key") String key,
            @Query("page") int page
    );
}
