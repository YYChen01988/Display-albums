package com.albums.albums

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.albums.R
import com.albums.albums.data.model.AlbumItem
import com.albums.albums.ui.activity.AlbumsActivity
import com.albums.albums.viewModel.AlbumsViewModel
import com.albums.core.network.Resource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext.unloadKoinModules
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any

@RunWith(AndroidJUnit4::class)
class InstrumentalTest {

    @Rule
    @JvmField
    var rule = ActivityTestRule(AlbumsActivity::class.java, true, false)

    lateinit var mockModule: Module
    lateinit var albumsViewModel: AlbumsViewModel


    @Before
    fun setUp() {
        albumsViewModel = Mockito.mock(AlbumsViewModel::class.java)
        mockModule = module {
            single(override = true) { albumsViewModel }
        }

        loadKoinModules(mockModule)
    }

    @After
    fun cleanUp() {
        unloadKoinModules(mockModule)
    }

    @Test
    fun sort_button_can_clicked() {
        `when`(albumsViewModel.getAlbums()).thenReturn(any())
        rule.launchActivity(null)
        onView(withId(R.id.btn)).perform(click())

    }

    @Test
    fun display_recyclerView() {
        `when`(albumsViewModel.getAlbums()).thenReturn(any())
        rule.launchActivity(null)
        onView(withId(R.id.rvAlbums)).check(matches(isDisplayed()))
    }
}