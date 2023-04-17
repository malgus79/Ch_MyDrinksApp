package com.mydrinksapp.viewmodel.fragments

import com.mydrinksapp.accessdata.JSONFileLoader
import com.mydrinksapp.app.AppConstants.BASE_URL
import com.mydrinksapp.data.model.Cocktail
import com.mydrinksapp.data.remote.ApiService
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchViewModelTest {

    private lateinit var api: ApiService

    companion object {
        private lateinit var retrofit: Retrofit

        @BeforeClass
        @JvmStatic
        fun setupCommon() {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    @Before
    fun setup() {
        api = retrofit.create(ApiService::class.java)
    }

    @Test
    fun `check fetch cocktail searched is not null test`() {
        runBlocking {
            val result = api.getCocktailByName("margarita")
            assertThat(result?.drinks, `is`(notNullValue()))
        }
    }

    @Test
    fun `check item cocktail searched for page test`() {
        runBlocking {
            val result = api.getCocktailByName("margarita")
            assertThat(result?.drinks?.size, `is`(6))
        }
    }

    @Test
    fun `check error fetch cocktail test`() {
        runBlocking {
            try {
                api.getCocktailByName("margarita")
            } catch (e: Exception) {
                assertThat(e.localizedMessage, `is`(""))
            }
        }
    }

    @Test
    fun `check cocktail searched remote with local test`() {
        runBlocking {
            val remoteResult = api.getCocktailByName("margarita")
            val localResult =
                JSONFileLoader().loadCocktail("cocktail_by_name_response_success")

            assertThat(
                localResult?.drinks?.size,
                `is`(remoteResult?.drinks?.size)
            )

            assertThat(
                localResult?.drinks.isNullOrEmpty(),
                `is`(remoteResult?.drinks?.isEmpty())
            )

            assertThat(
                localResult?.drinks?.contains(Cocktail("", "", "", "", "")),
                `is`(remoteResult?.drinks?.contains(Cocktail("", "", "", "", "")))
            )

            assertThat(
                localResult?.drinks?.indices,
                `is`(remoteResult?.drinks?.indices)
            )
        }
    }
}