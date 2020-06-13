package com.example.moviebuff

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.moviebuff.Repository.MoviePageListRepo
import com.example.moviebuff.Repository.NetworkState
import com.example.moviebuff.valueObject.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieListViewModel(private val movieRepository : MoviePageListRepo) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  moviePagedList : LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val  networkState : LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}