package com.movies.api

import com.my.favourite.movies.model.MovieSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface DefaultApi {

    /**
     * get Search Movie
     */
    @GET("/")
    fun getSearchMovie(
        @QueryMap headers: Map<String, String>
    ): Call<MovieSearchResponse>

}