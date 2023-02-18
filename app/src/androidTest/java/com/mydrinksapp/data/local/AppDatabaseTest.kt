package com.mydrinksapp.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.mydrinksapp.model.local.AppDatabase
import com.mydrinksapp.model.local.CocktailEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class AppDatabaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries().build()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testIsDatabaseNotOpen() {
        assertThat(database.isOpen).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testIsDatabaseOpen() = runTest {
        executeDatabaseFunction()
        assertThat(database.isOpen).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testDatabaseVersionIsCurrent() = runTest {
        executeDatabaseFunction()
        assertThat(database.openHelper.readableDatabase.version).isEqualTo(3)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testDatabasePathIsMemory() = runTest {
        executeDatabaseFunction()
        assertThat(database.openHelper.readableDatabase.path).isEqualTo(":memory:")
    }

    @After
    fun tearDown() {
        database.close()
    }

    @ExperimentalCoroutinesApi
    private fun executeDatabaseFunction() = runTest {
        val cocktailEntity = CocktailEntity(
            "1",
            "https://img1.mashed.com/img/gallery/heres-what-happens-when-you-drink-orange-juice-every-day/intro-1587655828.jpg",
            "Orange Juice", "A simple 100% orange juice",
            "Non_Alcoholic"
        )
        database.cocktailDao().saveCocktail(cocktailEntity)
    }

}