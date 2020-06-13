package com.example.moviebuff

import android.content.Intent
import android.graphics.Color.red
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.Toast


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviebuff.Api.MovieInterface
import com.example.moviebuff.Api.RetrofitClient
import com.example.moviebuff.Repository.MoviePageListRepo
import com.example.moviebuff.Repository.NetworkState
import com.example.moviebuff.movie_detail.singleMovie
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movie_list_item.*

class MainActivity : AppCompatActivity() {



    private lateinit var viewModel: MovieListViewModel
    lateinit var movieRepository: MoviePageListRepo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // new code


        val gridLayoutManager = GridLayoutManager(this, 2)

        val movieAdapter = MoviePageListAdapter(this)

        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.addItemDecoration(
            DividerItemDecoration(
                    rv_movie_list.context,
                (rv_movie_list.layoutManager as GridLayoutManager).orientation
                    )
        )
        rv_movie_list.adapter = movieAdapter

        // paste call here...

        val apiService : MovieInterface = RetrofitClient.getClient()

        movieRepository = MoviePageListRepo(apiService)

        viewModel = getViewModel()




        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return  1    // Movie_VIEW_TYPE will occupy 1 out of 2 span
                else return 2                                              // NETWORK_VIEW_TYPE will occupy all 2 span
            }
        };




        viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })



        viewModel.networkState.observe(this, Observer {

            txt_error_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE



            if (!(viewModel.listIsEmpty() && it == NetworkState.ERROR))
                rv_movie_list.visibility= View.VISIBLE

            if (it != NetworkState.LOADING || it == NetworkState.ERROR) {
                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.visibility = View.GONE
            }

            if (viewModel.listIsEmpty() && it == NetworkState.ERROR){
                button.visibility = View.VISIBLE
                button.setOnClickListener({
                    doagain()
                })
            }
            if (!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })

    }



    private fun getViewModel(): MovieListViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieListViewModel(movieRepository) as T
            }
        })[MovieListViewModel::class.java]
    }

    private fun doagain(){
        button.visibility = View.GONE
        val intent = intent
        finish()
        startActivity(intent)
       // startActivity(Intent(this@MainActivity, MainActivity::class.java))

        // we can also do one thing that we can again send request...
    }
    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmer()
    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmer()
        super.onPause()
    }



}