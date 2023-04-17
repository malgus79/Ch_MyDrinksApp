package com.mydrinksapp.app.di

import com.mydrinksapp.data.CocktailRepository
import com.mydrinksapp.domain.CocktailRepoInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoInterfaceModule {
    @Binds
    abstract fun datasource(impl: CocktailRepository): CocktailRepoInterface
}