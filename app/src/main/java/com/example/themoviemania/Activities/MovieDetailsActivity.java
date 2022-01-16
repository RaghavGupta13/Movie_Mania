package com.example.themoviemania.Activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.themoviemania.Models.Movie;
import com.example.themoviemania.R;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView poster_image;
    private TextView movie_overview, movie_title;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        poster_image = findViewById(R.id.id_details_imageView);
        movie_overview = findViewById(R.id.id_details_overview);
        movie_title = findViewById(R.id.id_title_details);
        ratingBar = findViewById(R.id.id_details_rating);

        getDataFromIntent();

    }

    private void getDataFromIntent(){
        if(getIntent().hasExtra("movie")){
            Movie movie = getIntent().getParcelableExtra("movie");

            movie_title.setText(movie.getTitle());
            movie_overview.setText(movie.getDescription());
            ratingBar.setRating(movie.getVote_average() / 2);

            Glide.with(MovieDetailsActivity.this)
                    .load("https://image.tmdb.org/t/p/w500/" + movie.getPoster_image_path())
                    .into(poster_image);
        }
    }
}