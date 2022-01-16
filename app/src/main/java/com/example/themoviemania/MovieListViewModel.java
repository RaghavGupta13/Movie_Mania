package com.example.themoviemania;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.themoviemania.Models.Movie;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    private MovieRepository repository;

    public MovieListViewModel(){
        repository = MovieRepository.getInstance();
    }

    public LiveData<List<Movie>> getMovies() {
        return repository.getMovies();
    }

    public LiveData<List<Movie>> getPopularMovies() {
        return repository.getPopularMovies();
    }

    public void searchMovies(String query, int pageNum){
        repository.searchMovie(query, pageNum);
    }

    public void popularMovies(int pageNum){
        repository.popularMovies(pageNum);
    }

    public void searchNextPage(){
        repository.searchNextPage();
    }
}
