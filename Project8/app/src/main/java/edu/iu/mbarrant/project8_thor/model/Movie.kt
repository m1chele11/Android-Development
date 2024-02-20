package edu.iu.mbarrant.project8_thor.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Director") val director: String,
    @SerializedName("Poster") val posterUrl: String,
    @SerializedName("Runtime") val runtime: String,
    @SerializedName("Genre") val genre: String,
    @SerializedName("imdbRating") val imdbRating: String,
    @SerializedName("imdbID") val imdbID: String
    // Add other properties as needed to represent the JSON response
)