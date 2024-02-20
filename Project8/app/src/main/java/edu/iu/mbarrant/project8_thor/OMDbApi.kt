package edu.iu.mbarrant.project8_thor

import retrofit2.Call
import edu.iu.mbarrant.project8_thor.model.Movie
import retrofit2.http.GET
import retrofit2.http.Query


public interface OMDbApi {
    @GET("/")
    fun getMovie(
        @Query("t") title: String,
        @Query("apikey") apiKey: String
    ): Call<Movie>
}