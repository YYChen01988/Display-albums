package com.albums.albums

import android.support.test.rule.ActivityTestRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.albums.R
import com.albums.albums.ui.activity.AlbumsActivity
import com.albums.albums.ui.fragment.AlbumsFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class InstrumentalTest {
    @get:Rule
    var rule = ActivityTestRule(AlbumsActivity::class.java)


    @Before
    fun setUp(){
//        scenario = launchFragmentInContainer()
//        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun sort_button_is_clicked(){
        onView(withId(com.albums.test.R.id.rvAlbums)).check(matches(isDisplayed()))
    }
}