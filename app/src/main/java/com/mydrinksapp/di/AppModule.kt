package com.mydrinksapp.di

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.mydrinksapp.data.local.AppDatabase
import com.mydrinksapp.data.remote.WebService
import com.mydrinksapp.utils.AppConstants.BASE_URL
import com.mydrinksapp.utils.AppConstants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomInstance(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideTragosDao(db: AppDatabase) = db.cocktailDao()

    @Singleton
    @Provides
    fun provideRetrofitInstance() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit) = retrofit.create(WebService::class.java)

}