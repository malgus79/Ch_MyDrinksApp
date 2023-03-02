package com.mydrinksapp.accessdata

import com.google.gson.Gson
import com.mydrinksapp.model.data.CategoriesList
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.net.HttpURLConnection

@RunWith(MockitoJUnitRunner::class)
class ResponseServerTest {
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `read json file success`() {
        val reader = JSONFileLoader().loadJSONString("category_response_success")
        assertThat(reader, `is`(notNullValue()))
        assertThat(reader, containsString("151 Florida Bushwacker"))
    }

    @Test
    fun `get cocktails and check title exist`() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(
                JSONFileLoader().loadJSONString("category_response_success")
                    ?: ""
            )
        mockWebServer.enqueue(response)

        assertThat(response.getBody()?.readUtf8(), containsString("strDrink"))
    }

    @Test
    fun `get cocktails and check fail response`() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(
                JSONFileLoader().loadJSONString("category_response_fail")
                    ?: ""
            )
        mockWebServer.enqueue(response)

        assertThat(
            response.getBody()?.readUtf8(),
            containsString("")
        )
    }

    @Test
    fun `get cocktails and check contains strDrink list no empty`() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(
                JSONFileLoader().loadJSONString("category_response_success")
                    ?: ""
            )
        mockWebServer.enqueue(response)

        assertThat(response.getBody()?.readUtf8(), containsString("strDrink"))

        val json =
            Gson().fromJson(response.getBody()?.readUtf8() ?: "", CategoriesList::class.java)
        assertThat(json.drinks.isEmpty(), `is`(false))
    }
}