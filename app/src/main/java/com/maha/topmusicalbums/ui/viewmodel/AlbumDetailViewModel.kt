package com.maha.topmusicalbums.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maha.topmusicalbums.data.repository.DataRepository
import com.maha.topmusicalbums.model.db.RealmAlbum
import com.maha.topmusicalbums.ui.state.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<State<RealmAlbum>>(State.Loading)
    val uiState: StateFlow<State<RealmAlbum>> = _uiState

    fun fetchAlbumById(albumId: Int) {
        viewModelScope.launch {
            try {
                val album = repository.fetchAlbumById(albumId)
                if (album != null) {
                    _uiState.value = State.Success(album)
                } else {
                    _uiState.value = State.Error("Album not found")
                }
            } catch (e: Exception) {
                _uiState.value = State.Error("Error: ${e.message}")
            }
        }
    }
}