package com.mydrinksapp.viewmodel.fragments

import com.mydrinksapp.accessdata.JSONFileLoader
import com.mydrinksapp.base.AppConstants.BASE_URL
import com.mydrinksapp.model.data.Cocktail
import com.mydrinksapp.model.data.Ingredient
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

class IngredientsViewModelTest {

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

    /*------------------------------ check cocktail by ingredient (Vodka) test ------------------------------*/
    @Test
    fun `check fetch cocktail by ingredient (Vodka) is not null test`() {
        runBlocking {
            val result = api.getCocktailByIngredients("Vodka")
            assertThat(result.drinks, `is`(notNullValue()))
        }
    }

    @Test
    fun `check item cocktail by ingredient (Vodka) for page test`() {
        runBlocking {
            val result = api.getCocktailByIngredients("Vodka")
            assertThat(result.drinks.size, `is`(94))
        }
    }

    @Test
    fun `check error fetch cocktail by ingredient (Vodka) test`() {
        runBlocking {
            try {
                api.getCocktailByIngredients("Vodka")
            } catch (e: Exception) {
                assertThat(e.localizedMessage, `is`(""))
            }
        }
    }

    @Test
    fun `check first item cocktail by ingredient (Vodka) test`() {
        runBlocking {
            val result = api.getCocktailByIngredients("Vodka")
            assertThat(
                result.drinks.first(), `is`(
                    Cocktail(
                        "15346",
                        "https://www.thecocktaildb.com/images/media/drink/yqvvqs1475667388.jpg",
                        "155 Belmont",
                        "",
                        "Non_Alcoholic"
                    )
                )
            )
        }
    }

    @Test
    fun `check cocktail by ingredient (Vodka) remote with local test`() {
        runBlocking {
            val remoteResult = api.getCocktailByIngredients("Vodka")
            val localResult =
                JSONFileLoader().loadCocktail("cocktail_by_ingredient_response_success")

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


    /*------------------------------ check ingredient by name (Vodka) test ------------------------------*/
    @Test
    fun `check fetch ingredient by name (Vodka) is not null test`() {
        runBlocking {
            val result = api.getIngredientsByName("Vodka")
            assertThat(result.ingredients, `is`(notNullValue()))
        }
    }

    @Test
    fun `check item ingredient by name (Vodka) for page test`() {
        runBlocking {
            val result = api.getIngredientsByName("Vodka")
            assertThat(result.ingredients.size, `is`(1))
        }
    }

    @Test
    fun `check error fetch ingredient by name (Vodka) test`() {
        runBlocking {
            try {
                api.getIngredientsByName("Vodka")
            } catch (e: Exception) {
                assertThat(e.localizedMessage, `is`(""))
            }
        }
    }

    @Test
    fun `check first item ingredient by name (Berries) test`() {
        runBlocking {
            val result = api.getIngredientsByName("Berries")
            assertThat(
                result.ingredients.first(), `is`(
                    Ingredient(
                        "54",
                        null,
                        "No",
                        null,
                        "Berries",
                        null
                    )
                )
            )
        }
    }

    @Test
    fun `check ingredient by name (Vodka) remote with local test`() {
        runBlocking {
            val remoteResult = api.getIngredientsByName("Vodka")
            val localResult =
                JSONFileLoader().loadIngredient("ingredient_by_name_response_success")

            assertThat(
                localResult?.ingredients?.size,
                `is`(remoteResult.ingredients.size)
            )

            assertThat(
                localResult?.ingredients.isNullOrEmpty(),
                `is`(remoteResult.ingredients.isEmpty())
            )

            assertThat(
                localResult?.ingredients?.contains(Ingredient()),
                `is`(remoteResult.ingredients.contains(Ingredient()))
            )

            assertThat(
                localResult?.ingredients?.indices,
                `is`(remoteResult.ingredients.indices)
            )
        }
    }
}