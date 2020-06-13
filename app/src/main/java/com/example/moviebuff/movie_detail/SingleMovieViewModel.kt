package com.example.moviebuff.movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviebuff.Repository.NetworkState
import com.example.moviebuff.valueObject.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel (private val movieRepo: MovieInfoRepo, movieId: Int) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  movieDetails : LiveData<MovieDetails> by lazy {
        movieRepo.fetchSingleMovieDetails(compositeDisposable,movieId)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieRepo.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}