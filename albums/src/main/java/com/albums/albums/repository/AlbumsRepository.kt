package com.albums.albums.repository

import com.albums.albums.data.AlbumsService
import com.albums.core.network.ResponseHandler

class AlbumsRepository(
    private val albumsService: AlbumsService,
    private val responseHandler: ResponseHandler
) {
    suspend fun getAlbums() = try {
        val response = albumsService.getAlbums()
        responseHandler.handleSuccess(data = response)
    } catch (e: Exception) {
        responseHandler.handleException(e)
    }
}