package com.example.healthandmind.di

import com.example.healthandmind.data.repository.network.NutritionixApiService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiKey

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppId

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideDotenv(): Dotenv = dotenv {
        directory = "/assets"
        filename = "env"
    }

    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(dotenv: Dotenv): String = dotenv["API_KEY"]

    @Provides
    @Singleton
    @AppId
    fun provideAppId(dotenv: Dotenv): String = dotenv["ID"]

    @Provides
    fun getOkHttp(
        @ApiKey apiKey: String,
        @AppId appId: String,
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor { chain ->
                chain
                    .request()
                    .newBuilder()
                    .addHeader("x-app-id", appId)
                    .addHeader("x-app-key", apiKey)
                    .build().let {
                        chain.proceed(it)
                    }
            }
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            .build()

    @Provides
    fun getRetrofit(client: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .client(client)
            .baseUrl("https://trackapi.nutritionix.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()

    @Provides
    fun getNutritionixApiService(retrofit: Retrofit): NutritionixApiService =
        retrofit.create(NutritionixApiService::class.java)

}
