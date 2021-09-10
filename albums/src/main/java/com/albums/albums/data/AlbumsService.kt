package com.albums.albums.data

class AlbumsService(private val albumsApi: AlbumsApi) {
    suspend fun getAlbums() = albumsApi.getAlbums()
}