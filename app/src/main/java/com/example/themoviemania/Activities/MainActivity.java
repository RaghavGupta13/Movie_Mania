package com.example.themoviemania.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.themoviemania.Adapters.MovieRecyclerView;
import com.example.themoviemania.Adapters.OnMovieListener;
import com.example.themoviemania.Models.Movie;
import com.example.themoviemania.MovieListViewModel;
import com.example.themoviemania.NetworkRequests.ApiClientService;
import com.example.themoviemania.NetworkRequests.MovieApiMethods;
import com.example.themoviemania.R;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMovieListener {

    private static final String TAG = "Main Activity";
    private MovieApiMethods api;
    private MovieListViewModel viewModel;
    private RecyclerView recyclerView;
    private MovieRecyclerView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        api = ApiClientService.getApi();
        viewModel = new ViewModelProvider(this).get(MovieListViewModel.class);


        recyclerView = findViewById(R.id.id_recycler_view);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Search view
        setupSearchView();

        configureRecyclerView();

        //for searching movies
        observeDataChange();

        //for popular movies
        observePopularMovies();

        //Getting Popular movies
        viewModel.popularMovies(1);

    }

    private void observePopularMovies(){
        viewModel.getPopularMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if(movies != null){
                    for(Movie movie: movies){
                        Log.d(TAG, "onChanged: "+ movie.getTitle());
                        adapter.setMovies(movies);
                    }
                }
            }
        });
    }

    private void observeDataChange(){
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if(movies != null){
                    for(Movie movie: movies){
                        Log.d(TAG, "onChanged: "+ movie.getTitle());
                        adapter.setMovies(movies);
                    }
                }
            }
        });
    }


    private void configureRecyclerView(){
        adapter = new MovieRecyclerView(this::onMovieClick);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        //RecyclerView Pagination
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(!recyclerView.canScrollVertically(1)){
                    viewModel.searchNextPage();
                }
            }
        });


    }

    @Override
    public void onMovieClick(int position) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movie", adapter.getSelectedMovieId(position));
        startActivity(intent);

    }

    private void setupSearchView(){
        final SearchView searchView = findViewById(R.id.id_search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //The "query" here is the string that we will get from the user
                viewModel.searchMovies(query, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }


}