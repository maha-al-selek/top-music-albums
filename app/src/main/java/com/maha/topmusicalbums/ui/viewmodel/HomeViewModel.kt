package com.maha.topmusicalbums.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maha.topmusicalbums.data.repository.DataRepository
import com.maha.topmusicalbums.extensions.toRealmList
import com.maha.topmusicalbums.model.api.Album
import com.maha.topmusicalbums.model.db.RealmAlbum
import com.maha.topmusicalbums.ui.state.State
import com.maha.topmusicalbums.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<State<List<RealmAlbum>>>(State.Loading)
    val uiState: StateFlow<State<List<RealmAlbum>>> = _uiState

    fun fetchAlbums(context: Context) {
        if (NetworkUtils.isInternetAvailable(context)) {
            fetchAlbumsFromApi()
        } else {
            fetchAlbumsFromDb()
        }
    }

    private fun fetchAlbumsFromApi() {
        viewModelScope.launch {
            try {
                val albumsFromNetwork = repository.fetchTopAlbumsFromNetwork()
                val realmAlbums = albumsFromNetwork.map {
                    it.toRealmAlbum()
                }
                if (realmAlbums.isNotEmpty()) {
                    repository.clearAlbums()
                    repository.saveAlbums(realmAlbums)
                    _uiState.value = State.Success(realmAlbums)
                } else {
                    _uiState.value = State.Error("No data found..")
                }
            } catch (e: Exception) {
                _uiState.value = State.Error("Error: ${e.message}")
            }
        }
    }

    private fun fetchAlbumsFromDb() {
        viewModelScope.launch {
            try {
                val albums = repository.fetchAlbums()
                if (albums.isNotEmpty()) {
                    _uiState.value = State.Success(albums)
                } else {
                    _uiState.value = State.Error("No data found..")
                }
            } catch (e: Exception) {
                _uiState.value = State.Error("Error: ${e.message}")
            }
        }
    }
}

private fun Album.toRealmAlbum(): RealmAlbum {
    return RealmAlbum().apply {
        id = this@toRealmAlbum.id
        artistName = this@toRealmAlbum.artistName
        name = this@toRealmAlbum.name
        albumThumbnail = this@toRealmAlbum.albumThumbnail
        genre = this@toRealmAlbum.genres?.toRealmList() ?: realmListOf()
        releaseDate = this@toRealmAlbum.releaseDate
        url = this@toRealmAlbum.url
        copyright = this@toRealmAlbum.copyright.toString()
    }
}
