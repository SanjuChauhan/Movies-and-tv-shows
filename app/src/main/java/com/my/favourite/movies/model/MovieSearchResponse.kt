package com.my.favourite.movies.model

import com.google.gson.annotations.SerializedName

class MovieSearchResponse {
    @SerializedName("Search")
    var search: List<MovieTitleData> = listOf()
}