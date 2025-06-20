package com.example.pract_2;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieApiClient {
    private static final String BASE_URL = "https://kinopoiskapiunofficial.tech/";
    private static final String API_TOKEN = "a3104a64-1786-4d8c-92eb-806acf986d20"; // Замените на ваш токен

    private KinopoiskApi apiService;

    public MovieApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(KinopoiskApi.class);
    }

    public void getMovieById(int id, final MovieCallback callback) {
        Call<MovieResponse> call = apiService.getMovieById(id, API_TOKEN);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    handleError(response.code(), callback);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }

    public void searchMovies(String keyword, final MovieListCallback callback) {
        Call<List<MovieResponse>> call = apiService.searchMovies(keyword, API_TOKEN);
        call.enqueue(new Callback<List<MovieResponse>>() {
            @Override
            public void onResponse(Call<List<MovieResponse>> call, Response<List<MovieResponse>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    handleError(response.code(), callback);
                }
            }

            @Override
            public void onFailure(Call<List<MovieResponse>> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }

    private void handleError(int statusCode, MovieCallback callback) {
        switch (statusCode) {
            case 404:
                callback.onError("Movie not found");
                break;
            case 500:
                callback.onError("Server error");
                break;
            default:
                callback.onError("Unknown error: " + statusCode);
        }
    }

    private void handleError(int statusCode, MovieListCallback callback) {
        switch (statusCode) {
            case 404:
                callback.onError("Movies not found");
                break;
            case 500:
                callback.onError("Server error");
                break;
            default:
                callback.onError("Unknown error: " + statusCode);
        }
    }

    public interface MovieCallback {
        void onSuccess(MovieResponse movie);
        void onError(String errorMessage);
    }

    public interface MovieListCallback {
        void onSuccess(List<MovieResponse> movies);
        void onError(String errorMessage);
    }
}
