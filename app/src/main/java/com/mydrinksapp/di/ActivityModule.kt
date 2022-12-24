package com.mydrinksapp.di
//
//import com.mydrinksapp.data.CocktailDataSource
//import com.mydrinksapp.data.DefaultCocktailCocktailDataSource
//import com.mydrinksapp.data.local.LocalDataSource
//import com.mydrinksapp.data.remote.RemoteCocktailDataSourceImpl
//import com.mydrinksapp.domain.Repo
//import com.mydrinksapp.domain.RepoImpl
//import dagger.Binds
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.components.ViewModelComponent
//
//@Module
//@InstallIn(ViewModelComponent::class)
//abstract class ActivityModule {
//
//    @Binds
//    abstract fun bindRepoImpl(repoImpl: RepoImpl): Repo
//
//    @Binds
//    abstract fun bindRemoteDatasourceImpl(remoteDataSourceImpl: RemoteCocktailDataSourceImpl): CocktailDataSource
//
//    @Binds
//    abstract fun bindLocalDataSourceImpl(localDataSourceImpl: LocalDataSource): CocktailDataSource
//}
//
//@Module
//@InstallIn(ViewModelComponent::class)  //TODO ver si es corresto el scope
//internal object MyActivityModule {
//    @Provides
//    fun provideDefaultCocktailDataSource(
//        networkCocktailDataSourceImpl: RemoteCocktailDataSourceImpl,
//        localDataSourceImpl: LocalDataSource
//    ) = DefaultCocktailCocktailDataSource(networkCocktailDataSourceImpl, localDataSourceImpl)
//}