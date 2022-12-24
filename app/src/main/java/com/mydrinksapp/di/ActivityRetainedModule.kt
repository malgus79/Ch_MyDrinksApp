package com.mydrinksapp.di

import com.mydrinksapp.data.CocktailDataSource
import com.mydrinksapp.data.DefaultCocktailDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {
    @Binds
    abstract fun dataSource(impl: DefaultCocktailDataSource): CocktailDataSource
}