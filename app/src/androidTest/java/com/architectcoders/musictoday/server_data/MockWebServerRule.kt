package com.architectcoders.musictoday.server_data

import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MockWebServerRule : TestWatcher(){
    lateinit var server: MockWebServer

    override fun starting(description: Description) {
        server = MockWebServer().apply {
            start(8080)
        }
    }

    override fun finished(description: Description) {
        server.shutdown()
    }
}