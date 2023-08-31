package com.mesum.showtimes.data.search

import com.google.gson.annotations.SerializedName
import com.mesum.showtimes.data.Result
import com.mesum.showtimes.data.ResultX

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class Movie(
    val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    val id: Int,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
)


fun Movie.toResult(): ResultX {
    return ResultX(
        adult = adult,
        backdrop_path = backdropPath ?: "",
        genre_ids = genreIds,
        id = id,
        original_language = originalLanguage,
        original_title = originalTitle,
        overview = overview,
        popularity = popularity,
        poster_path = posterPath ?: "",
        release_date = releaseDate,
        title = title,
        video = video,
        vote_average = voteAverage,
        vote_count = voteCount
    )
}