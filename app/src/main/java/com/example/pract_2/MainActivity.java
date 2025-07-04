package com.example.pract_2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MovieApiClient movieApiClient;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieApiClient = new MovieApiClient();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movieApiClient.getMovieById(1, new MovieApiClient.MovieCallback() {
            @Override
            public void onSuccess(MovieResponse movie) {
                Toast.makeText(MainActivity.this, "Movie found: " + movie.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        movieApiClient.searchMovies("matrix", new MovieApiClient.MovieListCallback() {
            @Override
            public void onSuccess(List<MovieResponse> movies) {
                movieAdapter = new MovieAdapter(movies);
                recyclerView.setAdapter(movieAdapter);
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}