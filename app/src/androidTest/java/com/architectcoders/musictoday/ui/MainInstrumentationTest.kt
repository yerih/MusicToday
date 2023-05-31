package com.architectcoders.musictoday.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.architectcoders.musictoday.data.database.ArtistDao
import com.architectcoders.musictoday.data.datasource.ArtistRemoteDataSource
import com.architectcoders.musictoday.server_data.MockWebServerRule
import com.architectcoders.musictoday.server_data.fromJson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import com.architectcoders.musictoday.R
import com.architectcoders.musictoday.matchers.DrawableTestMatcher.Companion.withDrawable
import okhttp3.OkHttpClient
import org.hamcrest.CoreMatchers.equalTo

@ExperimentalCoroutinesApi
@HiltAndroidTest
class MainInstrumentationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mockWSRule = MockWebServerRule()

    @get:Rule(order = 2)
    val locationPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        "android.permission.ACCESS_COARSE_LOCATION"
    )

    @get:Rule(order = 3)
    val activityRule = ActivityScenarioRule(NavHostActivity::class.java)

    @Inject
    lateinit var artistDao: ArtistDao

    @Inject
    lateinit var remoteDataSource: ArtistRemoteDataSource

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Before
    fun setUp(){
        mockWSRule.server.enqueue(MockResponse().fromJson("artists_gps_on.json"))
        hiltRule.inject()
        val resource = OkHttp3IdlingResource.create("Okhttp", okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun mock_ws_is_working() = runTest {
        val artists = remoteDataSource.getPopularArtists()
        artists.fold({throw Exception(it.toString())}){
            assertEquals("Bad Bunny", it[0].name)
        }
    }

    @Test
    fun click_on_artist_navigates_to_detail() {
        onView(withId(R.id.recycler)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click())
        )
        onView(withId(R.id.artist_detail_toolbar))
            .check(matches(hasDescendant(withText("Sech"))))
    }

    @Test
    fun icon_color_changes_when_favorite_btn_is_clicked_in_detail_screen() {
        onView(withId(R.id.recycler)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click())
        )
        onView(withId(R.id.btn_favorite))
            .perform(click())
//            .check(matches(withDrawable(R.drawable.ic_favorite_true)))
            .check(matches(hasDescendant(withId(R.drawable.ic_favorite_true)) ))
    }
}