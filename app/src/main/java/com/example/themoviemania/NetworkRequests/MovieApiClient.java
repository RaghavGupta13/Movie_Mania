package com.example.themoviemania.NetworkRequests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.themoviemania.AppExecutors;
import com.example.themoviemania.Models.Movie;
import com.example.themoviemania.Response.MovieListSearchResponse;
import com.example.themoviemania.Utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {
    private static final String TAG = "Movie Api Client";

    //Live Data for Searched Movies
    private MutableLiveData<List<Movie>> movies;

    //Live Data for Popular Movies
    private MutableLiveData<List<Movie>> moviesPop;

    private static MovieApiClient instance;

    private RetrieveMoviesRunnable retrieveMoviesRunnable;
    private RetrieveMoviesRunnablePop retrieveMoviesRunnablePop;


    private MovieApiClient(){
        movies = new MutableLiveData<>();
        moviesPop = new MutableLiveData<>();
    }

    public static MovieApiClient getInstance(){
        if(instance==null){
            instance = new MovieApiClient();
        }
        return instance;
    }

    public LiveData<List<Movie>> getMovies(){
        return movies;
    }

    public LiveData<List<Movie>> getPopularMovies(){
        return moviesPop;
    }

    //For searched movies
    public void searchMovies(String query, int page){

        if(retrieveMoviesRunnable != null){
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, page);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //cancelling the retrofit call
                myHandler.cancel(false);
            }
        }, 15000, TimeUnit.MILLISECONDS);
   }

   //For Popular Movies
   public void popularMovies(int page){
       if(retrieveMoviesRunnable != null){
           retrieveMoviesRunnable = null;
       }

       retrieveMoviesRunnablePop = new RetrieveMoviesRunnablePop(page);

       final Future myHandler2 = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnablePop);

       AppExecutors.getInstance().networkIO().schedule(new Runnable() {
           @Override
           public void run() {
               //cancelling the retrofit call
               myHandler2.cancel(false);
           }
       }, 15000, TimeUnit.MILLISECONDS);
   }


    //Retrieve data from REST Api by using a runnable class

    private class RetrieveMoviesRunnable implements Runnable{

        private String query;
        private int page;
        private boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int page) {
            this.query = query;
            this.page = page;
            this.cancelRequest = false;
        }

        @Override
        public void run() {

            //Get the response from the API in background

            try{

                Response response = getMovies(query, page).execute();

                //If we do not get response in 5 secs, we cancel the request
                if (cancelRequest) {
                    return;
                }

                if(response.code()==200){
                    List<Movie> movieList = new ArrayList<>(((MovieListSearchResponse)response.body()).getMovies());

                    if(page == 1){
                        //sending data to live data
                        movies.postValue(movieList);
                    }else {
                        List<Movie> currentMovies =  movies.getValue();
                        currentMovies.addAll(movieList);
                        movies.postValue(currentMovies);
                        Log.d(TAG, "run: " + response.code());
                    }

                }else{
                   String error = response.errorBody().string();
                    Log.d(TAG, "run: " + error);
                    movies.postValue(null);
                }

            }catch (IOException e){
                Log.d(TAG, "run: "+ e.getMessage());
                movies.postValue(null);
            }


        }

            private Call<MovieListSearchResponse> getMovies(String query, int page){
                return ApiClientService.getApi().getMovies(
                        Credentials.API_KEY,
                        query,
                        page
                );
            }

            private void cancelRequest(){
                Log.d(TAG, "cancelRequest: " + "Cancelling Search Request");
                cancelRequest = true;
            }
        }

    private class RetrieveMoviesRunnablePop implements Runnable{

        private int page;
        private boolean cancelRequest;

        public RetrieveMoviesRunnablePop(int page) {
            this.page = page;
            this.cancelRequest = false;
        }

        @Override
        public void run() {

            //Get the response from the API in background

            try{

                Response response2 = getPopularMovies(page).execute();

                //If we do not get response in 5 secs, we cancel the request
                if (cancelRequest) {
                    return;
                }

                if(response2.code()==200){
                    List<Movie> movieList = new ArrayList<>(((MovieListSearchResponse)response2.body()).getMovies());

                    if(page == 1){
                        //sending data to live data
                        moviesPop.postValue(movieList);
                    }else {
                        List<Movie> currentMovies =  movies.getValue();
                        currentMovies.addAll(movieList);
                        moviesPop.postValue(currentMovies);
                    }

                }else{
                    String error = response2.errorBody().string();
                    moviesPop.postValue(null);
                }

            }catch (IOException e){
                e.printStackTrace();
                moviesPop.postValue(null);
            }


        }

        private Call<MovieListSearchResponse> getPopularMovies(int page){
            return ApiClientService.getApi().getPopularMovies(
                    Credentials.API_KEY,
                    page
            );
        }

        private void cancelRequest(){
            Log.d(TAG, "cancelRequest: " + "Cancelling Search Request");
            cancelRequest = true;
        }
    }


}
