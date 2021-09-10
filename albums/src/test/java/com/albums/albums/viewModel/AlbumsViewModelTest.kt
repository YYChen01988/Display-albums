package com.albums.albums.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.albums.albums.data.model.AlbumItem
import com.albums.albums.repository.AlbumsRepository
import com.albums.albums.util.TestCoroutineRule
import com.albums.core.network.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AlbumsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var albumsRepository: AlbumsRepository

    @Mock
    private lateinit var apiAlbumsObserver: Observer<Resource<List<AlbumItem>>>

    private lateinit var viewModel: AlbumsViewModel

    @Before
    fun setUp() {
        viewModel = AlbumsViewModel(albumsRepository)
    }

    @Test
    fun `given server response 200 when fetch should return success`() {
        startFetchingAlbumsResult(Resource.success(data = emptyList()))
    }

    @Test
    fun `given server response error when fetch should return error`() {
        val errorMessage = "Dummy Error Message"
        startFetchingAlbumsResult(
            Resource.error(
                data = null,
                RuntimeException(errorMessage).toString()
            )
        )

    }

    private fun startFetchingAlbumsResult(data: Resource<List<AlbumItem>>) {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(data).`when`(albumsRepository).getAlbums()
            viewModel.fetchAllAlbums()
            viewModel.getAlbums().observeForever(apiAlbumsObserver)

            Mockito.verify(albumsRepository).getAlbums()
            Mockito.verify(apiAlbumsObserver).onChanged(data)
            viewModel.getAlbums().removeObserver(apiAlbumsObserver)
        }
    }
}