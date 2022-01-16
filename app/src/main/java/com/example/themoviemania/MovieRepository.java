package com.example.themoviemania;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.themoviemania.Models.Movie;
import com.example.themoviemania.NetworkRequests.MovieApiClient;

import java.util.List;

public class MovieRepository {

    private MovieApiClient movieApiClient;

    private static MovieRepository instance;

    private String mQuery;
    private int mPageNumber;

    public MovieRepository() {
        movieApiClient = MovieApiClient.getInstance();
    }

    public static MovieRepository getInstance(){

        if(instance==null){
            instance = new MovieRepository();
        }
        return instance;
    }

   public LiveData<List<Movie>> getMovies(){
        return movieApiClient.getMovies();
   }

    public LiveData<List<Movie>> getPopularMovies(){
        return movieApiClient.getPopularMovies();
    }

   public void searchMovie(String query, int pageNum){
        mQuery = query;
        mPageNumber = pageNum;
        movieApiClient.searchMovies(query, pageNum);
   }

    public void popularMovies(int pageNum){
        mPageNumber = pageNum;
        movieApiClient.popularMovies(pageNum);
    }

   public void searchNextPage(){
        searchMovie(mQuery, mPageNumber + 1);
   }
}
