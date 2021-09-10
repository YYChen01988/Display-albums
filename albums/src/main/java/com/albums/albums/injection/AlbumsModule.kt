package com.albums.albums.injection

import com.albums.albums.data.AlbumsApi
import com.albums.albums.data.AlbumsService
import com.albums.albums.repository.AlbumsRepository
import com.albums.albums.viewModel.AlbumsViewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val albumsModule = module {
    factory {
        AlbumsViewModel(
            albumsRepository = get()
        )
    }
    factory {
        val retrofit: Retrofit = get()
        retrofit.create(AlbumsApi::class.java)
    }
    factory {
        AlbumsService(get())
    }
    factory {
        AlbumsRepository(get(), get())
    }
}