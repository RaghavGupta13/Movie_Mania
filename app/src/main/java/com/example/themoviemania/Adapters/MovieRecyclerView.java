package com.example.themoviemania.Adapters;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.themoviemania.Models.Movie;
import com.example.themoviemania.R;

import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<MovieRecyclerView.MovieViewHolder> {

    private List<Movie> movies;
    private OnMovieListener onMovieListener;

    public MovieRecyclerView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(v, onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie currentMovie = movies.get(position);
        holder.ratingBar.setRating(currentMovie.getVote_average()/2);

        Log.d(TAG, "onBindViewHolder: " + "https://image.tmdb.org/t/p/w500/" + currentMovie.getPoster_image_path());

        //Using Glide Library to add images
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500/" + currentMovie.getPoster_image_path())
                .into(holder.poster_image);
    }

    @Override
    public int getItemCount() {
        if(movies != null){
            return movies.size();
        }
        else{
            return 0;
        }

    }

    public void setMovies(List<Movie> movies){
        this.movies = movies;
        notifyDataSetChanged();
    }

    public Movie getSelectedMovieId(int position){
        if(movies != null && movies.size()>0){
            return movies.get(position);
        }else {
            return null;
        }
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title, release_date, duration;
        private ImageView poster_image;
        private RatingBar ratingBar;
        private OnMovieListener onMovieListener;

        public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
            super(itemView);

            poster_image = itemView.findViewById(R.id.id_poster_image);
            ratingBar = itemView.findViewById(R.id.id_rating_bar);
            this.onMovieListener = onMovieListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onMovieListener.onMovieClick(getAdapterPosition());
        }


    }
}
