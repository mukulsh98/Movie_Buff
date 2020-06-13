package com.example.moviebuff.movie_detail

import androidx.lifecycle.LiveData
import com.example.moviebuff.Api.MovieInterface
import com.example.moviebuff.Repository.MovieDetailsNetworkSource
import com.example.moviebuff.Repository.NetworkState
import com.example.moviebuff.valueObject.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieInfoRepo (private val api: MovieInterface) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkSource

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetails> {

        movieDetailsNetworkDataSource = MovieDetailsNetworkSource(api,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse

    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }

}