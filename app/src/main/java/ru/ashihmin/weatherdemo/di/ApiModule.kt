package ru.ashihmin.weatherdemo.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import ru.ashihmin.weatherdemo.data.api.OpenWeatherMapApi
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor())
            .addInterceptor(AppIdInterceptor())
            .addInterceptor(OkHttpProfilerInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(json: Json, contentType: MediaType): Converter.Factory {
        return json.asConverterFactory(contentType)
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json { ignoreUnknownKeys = true }
    }

    @Provides
    @Singleton
    fun provideContentType(): MediaType {
        return "application/json".toMediaType()
    }

    @Provides
    @Singleton
    fun provideOpenWeatherMapApi(retrofit: Retrofit): OpenWeatherMapApi {
        return retrofit.create(OpenWeatherMapApi::class.java)
    }

    class AppIdInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
            val originalHttpUrl = chain.request().url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter(APP_ID_PARAM_NAME, API_KEY)
                .build()
            request.url(url)
            return chain.proceed(request.build())
        }
    }



    companion object {

        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        private const val APP_ID_PARAM_NAME = "APPID"
        private const val API_KEY = "38ce0dfd55d014204699ab7882568d1e"
    }

}
