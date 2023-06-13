package com.example.core.di

import android.content.Context
import com.example.core.datasource.LocalDataSource
import com.example.core.datasource.RemoteDataSource
import com.example.core.datasource.ShoppingCartPreferences
import com.example.core.db.network.FoodAPI
import com.example.core.repository.Repository

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class DataModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = Interceptor {
            val url = it.request()
                .url().newBuilder()
                .build()

            val request = it.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor it.proceed(request)
        }
        return OkHttpClient.Builder()
            .callTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .connectTimeout(2, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Singleton
    @Provides
    fun provideFoodAPI(retrofit: Retrofit): FoodAPI {
        return retrofit.create(FoodAPI::class.java)
    }


    @Singleton
    @Provides
    fun provideRemoteDataSource(foodAPI: FoodAPI): RemoteDataSource {
        return RemoteDataSource(foodAPI = foodAPI, mContext = context)
    }


    @Singleton
    @Provides
    fun provideShoppingPref(): ShoppingCartPreferences {
        return ShoppingCartPreferences(context)
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(shoppingCartPreferences: ShoppingCartPreferences): LocalDataSource {
        return LocalDataSource(shoppingCartPreferences)
    }


    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): Repository {
        return Repository(remoteDataSource = remoteDataSource, localDataSource = localDataSource)
    }
}