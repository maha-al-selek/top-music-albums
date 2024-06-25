package com.maha.topmusicalbums.data.repository

import com.maha.topmusicalbums.data.api.ApiService
import com.maha.topmusicalbums.model.api.Album
import com.maha.topmusicalbums.model.db.RealmAlbum
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val apiService: ApiService,
    private val realm: Realm
) {

    suspend fun fetchTopAlbumsFromNetwork(): List<Album> {
        val response = apiService.fetchAlbums()
        if (response.isSuccessful) {
            val copyRight = response.body()?.feed?.copyright ?: ""
            return response.body()?.feed?.results?.map {
                it.copy(copyright = copyRight)
            } ?: emptyList()
        } else {
            throw Exception("Network request failed")
        }
    }

    fun saveAlbums(albums: List<RealmAlbum>) {
        realm.writeBlocking {
            albums.forEach { album ->
                val existingAlbum = this.query<RealmAlbum>("id == $0", album.id).find().firstOrNull()
                if (existingAlbum != null) {
                    // Update the existing album
                    existingAlbum.apply {
                        artistName = album.artistName
                        name = album.name
                        albumThumbnail = album.albumThumbnail
                        genre?.clear() // Clear existing genres to avoid duplication
                        album.genre?.let { genre?.addAll(it) } // Add new genres
                        releaseDate = album.releaseDate
                        url = album.url
                        copyright = album.copyright
                    }
                } else {
                    // Insert the new album
                    copyToRealm(album)
                }
            }
        }
    }


    fun fetchAlbums(): List<RealmAlbum> {
        return realm.query<RealmAlbum>().find()
    }

    fun clearAlbums() {
        realm.writeBlocking {
            query<RealmAlbum>().find().forEach {
                delete(it)
            }
        }
    }

    fun fetchAlbumById(albumId: Int): RealmAlbum? {
        return realm.query<RealmAlbum>("id == $0", albumId).first().find()
    }

}