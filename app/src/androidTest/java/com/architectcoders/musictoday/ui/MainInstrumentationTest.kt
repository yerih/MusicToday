package com.architectcoders.musictoday.ui

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.architectcoders.appTestShared.FakeArtistDao
import com.architectcoders.appTestShared.FakeLocationHelper
import com.architectcoders.appTestShared.FakeRemoteService
import com.architectcoders.appTestShared.buildArtistDB
import com.architectcoders.musictoday.data.database.ArtistDao
import com.architectcoders.musictoday.data.datasource.ArtistRemoteDataSource
import com.architectcoders.musictoday.data.server.MusicService
import com.architectcoders.musictoday.server_data.fromJson
import com.architectcoders.musictoday.ui.common.LocationDataSource
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
class MainInstrumentationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val locationPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        "android.permission.ACCESS_COARSE_LOCATION"
    )

    @get:Rule(order = 2)
    val activityRule = ActivityScenarioRule(NavHostActivity::class.java)

    @Inject
    lateinit var artistDao: ArtistDao

    @Inject
    lateinit var remoteDataSource: ArtistRemoteDataSource

    var server: MockWebServer = MockWebServer()

    @Before
    fun setUp(){
//        server = MockWebServer().apply { start(8080) }
        server = MockWebServer()
        server.start(8080)
        server.enqueue(MockResponse().fromJson("artists_gps_on.json"))
        hiltRule.inject()
    }

    @After
    fun tearDown(){
        server.shutdown()
    }

    @Test
    fun check_5_items_from_db() = runTest {
        artistDao.insertArtists(buildArtistDB(1,2,3,4, 5))
        assertEquals(5, artistDao.artistCount())
    }

    @Test
    fun check_8_items_from_db() = runTest {
        artistDao.insertArtists(buildArtistDB(6,7,8,9,10,11,12,13))
        assertEquals(8, artistDao.artistCount())
    }

    @Test
    fun mock_ws_is_working() = runTest {
        val artists = remoteDataSource.getPopularArtists()
        artists.fold({throw Exception(it.toString())}){
            assertEquals("Bad Bunny", it[0].name)
        }
    }
}