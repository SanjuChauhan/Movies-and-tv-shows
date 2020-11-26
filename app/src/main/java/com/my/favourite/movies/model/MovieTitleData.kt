package com.my.favourite.movies.model

import com.google.gson.annotations.SerializedName

data class MovieTitleData(
    @SerializedName("Title")
    var title: String = "",
    @SerializedName("Year")
    var year: String = "",
    @SerializedName("imdbID")
    var imdbID: String = "",
    @SerializedName("Type")
    var type: String = "",
    @SerializedName("Poster")
    var poster: String = ""
)