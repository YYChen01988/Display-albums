package com.albums.albums

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.albums.R
import com.albums.albums.data.AlbumsApi
import com.albums.albums.data.AlbumsService
import com.albums.albums.data.model.AlbumItem
import com.albums.albums.repository.AlbumsRepository
import com.albums.albums.ui.activity.AlbumsActivity
import com.albums.albums.viewModel.AlbumsViewModel
import com.albums.core.network.ResponseHandler
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.unloadKoinModules
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class InstrumentalTest {

    @Rule
    @JvmField
    var rule = ActivityTestRule(AlbumsActivity::class.java, true, false)

    var mockModule = module {
        val albumsApi = TestApi()
        val albumService = AlbumsService(albumsApi)
        val responseHandler = ResponseHandler()
        val albumsRepository = AlbumsRepository(albumService, responseHandler)
        viewModel {
            AlbumsViewModel(albumsRepository)
        }

    }


    @Before
    fun setUp() {
        loadKoinModules(mockModule)
        rule.launchActivity(Intent())
    }

    @After
    fun cleanUp() {
        unloadKoinModules(mockModule)
    }

    @Test
    fun display_recyclerView() {
        onView(withId(R.id.rvAlbums)).check(matches(isDisplayed()))
        onView(withRecyclerView(R.id.rvAlbums)?.atPosition(0))
            .check(matches(hasDescendant(withText("ACDC"))));
        onView(withRecyclerView(R.id.rvAlbums)?.atPosition(1))
            .check(matches(hasDescendant(withText("ZZ"))))
    }

    @Test
    fun sort_button_can_clicked() {
        onView(withRecyclerView(R.id.rvAlbums)?.atPosition(0))
            .check(matches(hasDescendant(withText("ACDC"))));
        onView(withRecyclerView(R.id.rvAlbums)?.atPosition(1))
            .check(matches(hasDescendant(withText("ZZ"))))

        onView(withId(R.id.btn)).perform(click())

        onView(withRecyclerView(R.id.rvAlbums)?.atPosition(0))
            .check(matches(hasDescendant(withText("ZZ"))));
        onView(withRecyclerView(R.id.rvAlbums)?.atPosition(1))
            .check(matches(hasDescendant(withText("ACDC"))))

    }

    @Test
    fun sort_button_can_clicked_twice() {
        onView(withRecyclerView(R.id.rvAlbums)?.atPosition(0))
            .check(matches(hasDescendant(withText("ACDC"))));
        onView(withRecyclerView(R.id.rvAlbums)?.atPosition(1))
            .check(matches(hasDescendant(withText("ZZ"))))

        onView(withId(R.id.btn)).perform(click())

        onView(withRecyclerView(R.id.rvAlbums)?.atPosition(0))
            .check(matches(hasDescendant(withText("ZZ"))));
        onView(withRecyclerView(R.id.rvAlbums)?.atPosition(1))
            .check(matches(hasDescendant(withText("ACDC"))))

        onView(withId(R.id.btn)).perform(click())

        onView(withRecyclerView(R.id.rvAlbums)?.atPosition(0))
            .check(matches(hasDescendant(withText("ACDC"))));
        onView(withRecyclerView(R.id.rvAlbums)?.atPosition(1))
            .check(matches(hasDescendant(withText("ZZ"))))

    }

}

class TestApi : AlbumsApi {
    override suspend fun getAlbums(): List<AlbumItem> {
        return listOf(
            AlbumItem(1, 2, "ZZ"),
            AlbumItem(1, 1, "ACDC")
        )
    }
}

fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher? {
    return RecyclerViewMatcher(recyclerViewId)
}
