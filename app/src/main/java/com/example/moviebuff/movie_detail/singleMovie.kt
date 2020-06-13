package com.example.moviebuff.movie_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.moviebuff.Api.MovieInterface
import com.example.moviebuff.Api.POSTER_BASE_URL
import com.example.moviebuff.Api.RetrofitClient
import com.example.moviebuff.R
import com.example.moviebuff.Repository.NetworkState
import com.example.moviebuff.valueObject.MovieDetails
import kotlinx.android.synthetic.main.activity_single_movie.*

class singleMovie : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepo: MovieInfoRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)


        val movieId: Int = intent.getIntExtra("id",1)

        dowork(movieId)

    }

    fun dowork(movieId: Int){

        val apiService : MovieInterface = RetrofitClient.getClient()
        movieRepo = MovieInfoRepo(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })

    }

    fun bindUI ( it:MovieDetails){

        movie_title.text= it.title
        BigTitle.text= it.title
        movie_rating.text= it.rating.toString()
        movie_release_date.text= it.releaseDate
        movie_overview.text= it.overview
        movie_votecount.text= it.voteCount.toString()
        movie_populartity.text = it.popularity.toString()
        movie_tagline.text = it.tagline
        movie_language.text = it.originalLanguage


        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster);

        val moviePosterURL1 = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL1)
            .into(iv_movie_poster1);
    }



    private fun getViewModel(movieId:Int): SingleMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieRepo,movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}