package com.kavis.androidtask.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kavis.androidtask.BASE_URL
import com.kavis.androidtask.data.remote.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): HeaderInterceptor = HeaderInterceptor()


    @Provides
    @Singleton
    fun provideOKHttpClient(loggingInterceptor: HttpLoggingInterceptor, headerInterceptor: HeaderInterceptor): OkHttpClient
    = OkHttpClient().run {
        OkHttpClient.Builder().run {
            addInterceptor(loggingInterceptor)
            addInterceptor(headerInterceptor)
            build()
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}