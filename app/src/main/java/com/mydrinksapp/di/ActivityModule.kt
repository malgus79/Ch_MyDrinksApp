package com.mydrinksapp.di

import com.mydrinksapp.data.DataSource
import com.mydrinksapp.data.DefaultCocktailDataSource
import com.mydrinksapp.data.local.LocalDataSourceImpl
import com.mydrinksapp.data.remote.RemoteDataSourceImpl
import com.mydrinksapp.domain.Repo
import com.mydrinksapp.domain.RepoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ActivityModule {

    @Binds
    abstract fun bindRepoImpl(repoImpl: RepoImpl): Repo

    @Binds
    abstract fun bindRemoteDatasourceImpl(remoteDataSourceImpl: RemoteDataSourceImpl): DataSource

    @Binds
    abstract fun bindLocalDataSourceImpl(localDataSourceImpl: LocalDataSourceImpl): DataSource
}

@Module
@InstallIn(ViewModelComponent::class)  //TODO ver si es corresto el scope
internal object MyActivityModule {
    @Provides
    fun provideDefaultCocktailDataSource(
        networkCocktailDataSourceImpl: RemoteDataSourceImpl,
        localDataSourceImpl: LocalDataSourceImpl
    ) = DefaultCocktailDataSource(networkCocktailDataSourceImpl, localDataSourceImpl)
}