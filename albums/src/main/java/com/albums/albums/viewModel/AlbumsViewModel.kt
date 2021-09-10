package com.albums.albums.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albums.albums.data.model.AlbumItem
import com.albums.albums.repository.AlbumsRepository
import com.albums.core.network.Resource
import kotlinx.coroutines.launch

class AlbumsViewModel (
    private val albumsRepository: AlbumsRepository,
) : ViewModel() {
    private val allAlbums: MutableLiveData<Resource<List<AlbumItem>>> = MutableLiveData<Resource<List<AlbumItem>>>()

    fun fetchAllAlbums() {
        viewModelScope.launch {
            allAlbums.postValue(Resource.loading(data = null))
            allAlbums.postValue(albumsRepository.getAlbums())
        }
    }

    fun getAlbums() = allAlbums
}