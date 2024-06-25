package com.maha.topmusicalbums.data.api

import com.maha.topmusicalbums.model.api.Data
import retrofit2.http.GET
import retrofit2.Response

interface ApiService {
    @GET("us/music/most-played/100/albums.json")
    suspend fun fetchAlbums(): Response<Data>
}
