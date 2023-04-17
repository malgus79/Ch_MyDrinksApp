package com.mydrinksapp.viewmodel.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mydrinksapp.accessdata.JSONFileLoader
import com.mydrinksapp.app.AppConstants.BASE_URL
import com.mydrinksapp.data.model.Categories
import com.mydrinksapp.data.model.CocktailByCategory
import com.mydrinksapp.data.remote.ApiService
import com.mydrinksapp.viewmodel.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModelTest {

    @get:Rule
    val instantExecutionerRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutinesRule = MainDispatcherRule()

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

    /*------------------------------ check is not null test ------------------------------*/
    @Test
    fun `check fetch cocktails random is not null test`() {
        runBlocking {
            val result = api.getRandomCocktails()
            assertThat(result, `is`(notNullValue()))
        }
    }

    @Test
    fun `check fetch cocktails by categories is not null test`() {
        runBlocking {
            val result = api.getCocktailsByCategories("Shake")
            assertThat(result, `is`(notNullValue()))
        }
    }

    @Test
    fun `check fetch all categories list is not null test`() {
        runBlocking {
            val result = api.getAllCategoriesList("list")
            assertThat(result, `is`(notNullValue()))
        }
    }

    /*------------------------------ check items test ------------------------------*/
    @Test
    fun `check items of cocktails random test`() {
        runBlocking {
            val result = api.getRandomCocktails()
            assertThat(result.drinks.size, `is`(1))
        }
    }

    @Test
    fun `check items of cocktails by categories test`() {
        runBlocking {
            val result = api.getCocktailsByCategories("Shake")
            assertThat(result.drinks.size, `is`(17))
        }
    }

    @Test
    fun `check items of all categories list test`() {
        runBlocking {
            val result = api.getAllCategoriesList("list")
            assertThat(result.drinks.size, `is`(11))
        }
    }

    /*------------------------------ check first item test ------------------------------*/
    @Test
    fun `check first item cocktails by categories test`() {
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
    fun `check first item all categories list test`() {
        runBlocking {
            val result = api.getAllCategoriesList("list")
            assertThat(
                result.drinks.first(), `is`(
                    Categories(
                        "Ordinary Drink"
                    )
                )
            )
        }
    }

    /*------------------------------ check remote with local test ------------------------------*/
    @Test
    fun `check cocktails by category remote with local test`() {
        runBlocking {
            val remoteResult = api.getCocktailsByCategories("Shake")
            val localResult =
                JSONFileLoader().loadCocktailByCategory("category_response_success")

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

    @Test
    fun `check all categories list remote with local test`() {
        runBlocking {
            val remoteResult = api.getAllCategoriesList("list")
            val localResult =
                JSONFileLoader().loadCocktailList("categories_list_response_success")

            assertThat(
                localResult?.drinks?.size,
                `is`(remoteResult.drinks.size)
            )

            assertThat(
                localResult?.drinks.isNullOrEmpty(),
                `is`(remoteResult.drinks.isEmpty())
            )

            assertThat(
                localResult?.drinks?.contains(Categories()),
                `is`(remoteResult.drinks.contains(Categories()))
            )

            assertThat(
                localResult?.drinks?.indices,
                `is`(remoteResult.drinks.indices)
            )
        }
    }
}