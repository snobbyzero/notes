package com.example.notes.di.module

import com.example.notes.retrofit.NotesRetrofitService
import com.example.notes.retrofit.SessionRetrofitService
import com.example.notes.retrofit.TokenInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://bnet.i-partner.ru/testAPI/")
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Provides
    @Singleton
    fun provideOkHttp(
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideSessionRetrofitService(retrofit: Retrofit) : SessionRetrofitService =
        retrofit.create(SessionRetrofitService::class.java)

    @Provides
    @Singleton
    fun provideNotesRetrofitService(retrofit: Retrofit) : NotesRetrofitService =
        retrofit.create(NotesRetrofitService::class.java)
}