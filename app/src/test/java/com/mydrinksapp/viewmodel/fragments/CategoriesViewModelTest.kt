package com.mydrinksapp.viewmodel.fragments

import com.mydrinksapp.accessdata.JSONFileLoader
import com.mydrinksapp.app.AppConstants.BASE_URL
import com.mydrinksapp.data.model.CocktailByCategory
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

class CategoriesViewModelTest {

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
    fun `check fetch categories is not null test`() {
        runBlocking {
            val result = api.getCocktailsByCategories("Shake")
            assertThat(result.drinks, `is`(notNullValue()))
        }
    }

    @Test
    fun `check item categories for page test`() {
        runBlocking {
            val result = api.getCocktailsByCategories("Shake")
            assertThat(result.drinks.size, `is`(17))
        }
    }

    @Test
    fun `check error fetch categories test`() {
        runBlocking {
            try {
                api.getCocktailsByCategories("Shake")
            } catch (e: Exception) {
                assertThat(e.localizedMessage, `is`(""))
            }
        }
    }

    @Test
    fun `check first item categories test`() {
        runBlocking {
            val result = api.getCocktailsByCategories("Shake")
            assertThat(
                result.drinks.first(), `is`(
                    CocktailByCategory(
                        "14588",
                        "151 Florida Bushwacker",
                        "https://www.thecocktaildb.com/images/media/drink/rvwrvv1468877323.jpg"
                    )
                )
            )
        }
    }

    @Test
    fun `check categories remote with local test`() {
        runBlocking {
            val remoteResult = api.getCocktailsByCategories("Shake")
            val localResult = JSONFileLoader().loadCocktailByCategory("category_response_success")

            assertThat(
                localResult?.drinks?.size,
                `is`(remoteResult.drinks.size)
            )

            assertThat(
                localResult?.drinks.isNullOrEmpty(),
                `is`(remoteResult.drinks.isEmpty())
            )

            assertThat(
                localResult?.drinks?.contains(CocktailByCategory()),
                `is`(remoteResult.drinks.contains(CocktailByCategory()))
            )

            assertThat(
                localResult?.drinks?.indices,
                `is`(remoteResult.drinks.indices)
            )
        }
    }
}