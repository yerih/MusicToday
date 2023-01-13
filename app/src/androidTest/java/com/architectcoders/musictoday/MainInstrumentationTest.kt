package com.architectcoders.musictoday

import androidx.test.core.app.ActivityScenario
import androidx.test.rule.GrantPermissionRule
import org.junit.Rule


class MainInstrumentationTest {
    @get:Rule
    val locationPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        "android.permission.ACCESS_COARSE_LOCATION"
    )

    @get:Rule
//    val activityRule = ActivityScenario
}