package com.example.pract_2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KinopoiskApi {
    @GET("api/v2.2/films/{id}")
    Call<MovieResponse> getMovieById(@Path("id") int id, @Query("token") String token);

    @GET("api/v2.2/films")
    Call<List<MovieResponse>> searchMovies(@Query("keyword") String keyword, @Query("token") String token);
}
