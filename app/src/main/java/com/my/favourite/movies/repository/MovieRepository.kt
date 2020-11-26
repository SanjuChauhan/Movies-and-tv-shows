package com.my.favourite.movies.repository

import com.my.favourite.movies.api.APIManager
import com.my.favourite.movies.api.APIResponseListener

open class MovieRepository {

    /**
     * Get Movie List
     */
    fun getSearchMovie(queryMap: Map<String, String>, listener: APIResponseListener) {
        val call = APIManager.instance?.defaultAPI?.getSearchMovie(queryMap)
        APIManager.instance?.callAPI(call, listener)
    }

    companion object {
        @JvmStatic
        val movieRepository: MovieRepository
            get() {
                return MovieRepository()
            }
    }
}
