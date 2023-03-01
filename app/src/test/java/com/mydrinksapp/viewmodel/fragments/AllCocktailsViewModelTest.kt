package com.mydrinksapp.viewmodel.fragments

import com.mydrinksapp.accessdata.JSONFileLoader
import com.mydrinksapp.base.AppConstants.BASE_URL
import com.mydrinksapp.model.data.Cocktail
import com.mydrinksapp.model.remote.ApiService
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AllCocktailsViewModelTest {
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
    fun `check fetch cocktail by letter (A) is not null test`() {
        runBlocking {
            val result = api.getCocktailByLetter("A")
            assertThat(result.drinks, `is`(notNullValue()))
        }
    }

    @Test
    fun `check item cocktail by letter (A) for page test`() {
        runBlocking {
            val result = api.getCocktailByLetter("A")
            assertThat(result.drinks.size, `is`(25))
        }
    }

    @Test
    fun `check error fetch cocktail by letter (A) test`() {
        runBlocking {
            try {
                api.getCocktailByLetter("A")
            } catch (e: Exception) {
                assertThat(e.localizedMessage, `is`(""))
            }
        }
    }

    @Test
    fun `check first item cocktail by letter (A) test`() {
        runBlocking {
            val result = api.getCocktailByLetter("A")
            assertThat(
                result.drinks.first(), `is`(
                    Cocktail(
                        "17222",
                        "https://www.thecocktaildb.com/images/media/drink/2x8thr1504816928.jpg",
                        "A1",
                        "Pour all ingredients into a cocktail shaker, mix and serve over ice into a chilled glass.",
                        "Alcoholic"
                    )
                )
            )
        }
    }

    @Test
    fun `check cocktail by letter (A) remote with local test`() {
        runBlocking {
            val remoteResult = api.getCocktailByLetter("A")
            val localResult =
                JSONFileLoader().loadCocktailByLetter("cocktail_by_letter_response_success")

            assertThat(
                localResult?.drinks?.size,
                `is`(remoteResult.drinks.size)
            )

            assertThat(
                localResult?.drinks.isNullOrEmpty(),
                `is`(remoteResult.drinks.isEmpty())
            )

            assertThat(
                localResult?.drinks?.contains(Cocktail()),
                `is`(remoteResult.drinks.contains(Cocktail()))
            )

            assertThat(
                localResult?.drinks?.indices,
                `is`(remoteResult.drinks.indices)
            )
        }
    }
}