package com.tech.hilt_mvvm.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tech.hilt_mvvm.data.network.ApiInterface
import com.tech.hilt_mvvm.data.network.NetworkConnectionInterceptor
import com.tech.hilt_mvvm.data.preferences.PreferenceProvider
import com.tech.hilt_mvvm.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


@InstallIn(SingletonComponent::class)
@Module
internal class AppModule {

    @Provides
    fun providesContext(app: Application): Context {
        return app.applicationContext
    }

    @Provides
    fun providesGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    fun providesOkHttpClient(context: Context): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        val networkConnectionInterceptor = NetworkConnectionInterceptor(context)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                    return arrayOf()
                }
            }
        )

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        val sslSocketFactory = sslContext.socketFactory
        val builder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor)
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { hostname, session -> true }
        return builder.build()

    }

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl("https.dummy")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
        // .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        return builder.build()
    }

    @Provides
    fun providesApiServices(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    fun preferencesProvider(context: Context): PreferenceProvider {
        return PreferenceProvider(context)
    }

    @Provides
    fun repository(pref: PreferenceProvider, apiService: ApiInterface): Repository {
        return Repository(pref, apiService)
    }
}