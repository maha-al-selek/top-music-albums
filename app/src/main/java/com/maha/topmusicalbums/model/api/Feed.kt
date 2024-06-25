package com.maha.topmusicalbums.model.api

import com.google.gson.annotations.SerializedName

data class Data(
    val feed: Feed
)

data class Feed(
    val title: String,
    val copyright: String,
    val results: List<Album>,
)

data class Album (
    val id: Int,
    val artistName: String,
    val name: String,
    @SerializedName("artworkUrl100")
    val albumThumbnail: String,
    val genres: List<Genre>?,
    val releaseDate: String,
    val url: String,
    var copyright: String?=""
)

data class Genre (
    val genreId: Int,
    val name: String
)

