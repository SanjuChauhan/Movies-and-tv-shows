package com.my.favourite.movies.ui.activity

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.my.favourite.movies.R
import com.my.favourite.movies.databinding.ActivityMainBinding
import com.my.favourite.movies.model.MovieTitleData
import com.my.favourite.movies.ui.adapter.MovieDataRvAdapter
import com.my.favourite.movies.viewmodel.MovieViewModel
import timber.log.Timber


class SearchMovieActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var movieViewModel: MovieViewModel
    private var movieDataRvAdapter: MovieDataRvAdapter? = null
    var isLoadMoreFeedPeopleListData: Boolean = true
    var isFromSearch: Boolean = false
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        binding.viewModel = movieViewModel

        setSupportActionBar(binding.toolbar);

        initObserver()
        initAdapter()

        getSearchedMovie()
    }

    /***
     * This method is use to initialize observer
     */
    private fun initObserver() {
        movieViewModel.strToastMessage.observe(
            this,
            Observer { message -> showToast(message) })
        movieViewModel.movieTitlesListMutableData.observe(this, Observer { data ->
            Timber.e("List Size : %s", data.size)
            if (isFromSearch) {
                movieDataRvAdapter?.clear()
            }
            if (data.isEmpty() && movieDataRvAdapter?.size() == 0) {
                binding.tvEmpty.visibility = View.VISIBLE
            } else {
                binding.tvEmpty.visibility = View.GONE
                movieDataRvAdapter?.addAll(data);
            }
            isLoadMoreFeedPeopleListData = true
        })
        movieViewModel.strSearch.observe(this, Observer { str ->
            Timber.e("Search %s", str)
            if (str.length >= 3) {
                currentPage = 1
                isFromSearch = true
                getSearchedMovie(str, currentPage)
            }
        })
    }

    private fun initAdapter() {
        val linearLayoutManager = LinearLayoutManager(this)
        binding.rvMovie.layoutManager = linearLayoutManager

        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider)!!)
        binding.rvMovie.addItemDecoration(itemDecorator)

        movieDataRvAdapter = MovieDataRvAdapter(this)
        binding.rvMovie.adapter = movieDataRvAdapter
        movieDataRvAdapter?.setItemClickListener(object :
            MovieDataRvAdapter.OnItemClickListener {
            override fun onItemClick(data: MovieTitleData, position: Int) {
                //
            }
        })
        setUpPaginationScroll(linearLayoutManager)
    }


    private fun setUpPaginationScroll(linearLayoutManager: LinearLayoutManager) {
        binding.rvMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount: Int = linearLayoutManager.childCount
                val totalItemCount: Int = linearLayoutManager.itemCount
                val firstVisibleItemPosition: Int =
                    linearLayoutManager.findFirstVisibleItemPosition()

                if (isLoadMoreFeedPeopleListData && visibleItemCount + firstVisibleItemPosition >= totalItemCount) {
                    Timber.e("Call api for new data")
                    isLoadMoreFeedPeopleListData = false
                    isFromSearch = false
                    currentPage += 1
                    if (movieViewModel.strSearch.value!!.isNotEmpty()) {
                        getSearchedMovie(movieViewModel.strSearch.value!!, currentPage)
                    } else {
                        getSearchedMovie(page = currentPage)
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    /**
     * Get Searched Movie
     */
    private fun getSearchedMovie(strSearch: String = "batman", page: Int = 1) {
        if (checkNetworkState()) {
            movieViewModel.callSearchMovieAPI(strSearch, page)
        } else {
            movieViewModel.strToastMessage.postValue(getString(R.string.msg_no_internet))
        }
    }

}