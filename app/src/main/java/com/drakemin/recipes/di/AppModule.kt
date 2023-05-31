package com.drakemin.recipes.di

import android.app.Application
import androidx.room.Room
import com.drakemin.recipes.data.local.RecipeDatabase
import com.drakemin.recipes.data.remote.RecipeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRecipeApi(): RecipeApi {
        return Retrofit.Builder()
            .baseUrl(RecipeApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

//    @Provides
//    @Singleton
//    fun provideOkhttpClient():OkHttpClient{
//        val loggingInterceptor = HttpLoggingInterceptor()
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//
//        return OkHttpClient()
//            .newBuilder()
//            .addInterceptor(loggingInterceptor)
//            .build()
//    }

    @Provides
    @Singleton
    fun provideRecipeDatabase(app: Application): RecipeDatabase {
        return Room.databaseBuilder(
            app,
            RecipeDatabase::class.java,
            "recipe.db"
        ).build()
    }

}