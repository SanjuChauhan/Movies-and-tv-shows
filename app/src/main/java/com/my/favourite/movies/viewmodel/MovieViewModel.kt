package com.my.favourite.movies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.my.favourite.movies.api.APIResponseListener
import com.my.favourite.movies.constants.API_KEY
import com.my.favourite.movies.constants.API_KEY_VALUE
import com.my.favourite.movies.constants.PAGE_KEY
import com.my.favourite.movies.constants.SEARCH_KEY
import com.my.favourite.movies.model.MovieSearchResponse
import com.my.favourite.movies.model.MovieTitleData
import com.my.favourite.movies.repository.MovieRepository.Companion.movieRepository

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    val strToastMessage = MutableLiveData<String>()
    val isShowProgressDialog = MutableLiveData<Boolean>()
    val strSearch = MutableLiveData<String>("")
    val movieTitlesListMutableData = MutableLiveData<List<MovieTitleData>>()

    /**
     * Call Search Movie API.
     */
    fun callSearchMovieAPI(strSearch: String, page: Int) {
        isShowProgressDialog.postValue(true)
        val queryMap = HashMap<String, String>()
        queryMap[SEARCH_KEY] = strSearch
        queryMap[PAGE_KEY] = page.toString()
        queryMap[API_KEY] = API_KEY_VALUE

        movieRepository.getSearchMovie(queryMap, object : APIResponseListener {
            override fun onSuccess(response: Any?) {
                val searchResponse = response as MovieSearchResponse
                isShowProgressDialog.postValue(false)
                movieTitlesListMutableData.postValue(searchResponse.search)
            }

            override fun onFail(message: String?) {
                strToastMessage.postValue(message)
                isShowProgressDialog.postValue(false)
            }
        })
    }
}