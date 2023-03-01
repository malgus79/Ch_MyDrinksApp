package com.mydrinksapp.viewmodel.fragments

import com.mydrinksapp.accessdata.JSONFileLoader
import com.mydrinksapp.base.AppConstants
import com.mydrinksapp.model.data.Cocktail
import com.mydrinksapp.model.remote.ApiService
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailViewModelTest {

    private lateinit var api: ApiService

    companion object {
        private lateinit var retrofit: Retrofit

        @BeforeClass
        @JvmStatic
        fun setupCommon() {
            retrofit = Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    @Before
    fun setup() {
        api = retrofit.create(ApiService::class.java)
    }

    @Test
    fun `check fetch cocktail by id (id = 11007) is not null test`() {
        runBlocking {
            val result = api.getCocktailById("11007")
            MatcherAssert.assertThat(result.drinks, Matchers.`is`(Matchers.notNullValue()))
        }
    }

    @Test
    fun `check item cocktail by id (id = 11007) for page test`() {
        runBlocking {
            val result = api.getCocktailById("11007")
            MatcherAssert.assertThat(result.drinks.size, Matchers.`is`(1))
        }
    }

    @Test
    fun `check error fetch cocktail by id (id = 11007) test`() {
        runBlocking {
            try {
                api.getCocktailById("11007")
            } catch (e: Exception) {
                MatcherAssert.assertThat(e.localizedMessage, Matchers.`is`(""))
            }
        }
    }

    @Test
    fun `check cocktail by id (id = 11007) remote with local test`() {
        runBlocking {
            val remoteResult = api.getCocktailById("11007")
            val localResult =
                JSONFileLoader().loadCocktail("cocktail_by_id_response_success")

            MatcherAssert.assertThat(
                localResult?.drinks?.size,
                Matchers.`is`(remoteResult.drinks.size)
            )

            MatcherAssert.assertThat(
                localResult?.drinks.isNullOrEmpty(),
                Matchers.`is`(remoteResult.drinks.isEmpty())
            )

            MatcherAssert.assertThat(
                localResult?.drinks?.contains(Cocktail("", "", "", "", "")),
                Matchers.`is`(remoteResult.drinks.contains(Cocktail("", "", "", "", "")))
            )

            MatcherAssert.assertThat(
                localResult?.drinks?.indices,
                Matchers.`is`(remoteResult.drinks.indices)
            )
        }
    }
}