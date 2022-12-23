package com.mydrinksapp.di

import com.mydrinksapp.data.DataSourceImpl
import com.mydrinksapp.domain.DataSource
import com.mydrinksapp.domain.Repo
import com.mydrinksapp.domain.RepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ActivityModule {

    @Binds
    abstract fun bindRepoImpl(repoImpl: RepoImpl): Repo

    @Binds
    abstract fun bindDataSourceImpl(dataSourceImpl: DataSourceImpl): DataSource
}