package com.example.moviebuff.valueObject


import com.google.gson.annotations.SerializedName

data class Movie(

    val id: Int,

    @SerializedName("poster_path")
    val posterPath: String,

    val title: String

)