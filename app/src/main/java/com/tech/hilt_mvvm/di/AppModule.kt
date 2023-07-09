package com.tech.hilt_mvvm.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tech.hilt_mvvm.data.network.ApiInterface
import com.tech.hilt_mvvm.data.preferences.PreferenceProvider
import com.tech.hilt_mvvm.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
   fun providesContext(@ApplicationContext context: Context):Context {
       return context
   }

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit {
        val mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            .build()
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://fakestoreapi.com/")
            .client(mOkHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiInterface = retrofit.create(ApiInterface::class.java)

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context):PreferenceProvider =  PreferenceProvider(context)

   @Singleton
   @Provides
   fun provideRepository(apiInterface: ApiInterface, preferenceProvider: PreferenceProvider):Repository = Repository(apiInterface, preferenceProvider)
}