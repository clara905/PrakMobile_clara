package com.app.SIAKAD.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    /*
     * Gunakan:
     *   http://10.0.2.2:3000/   → jika menggunakan Emulator Android Studio
     *   http://192.168.x.x:3000/ → jika menggunakan HP fisik (ganti dengan IP komputer)
     *
     * JANGAN gunakan http://localhost:3000/ dari Android karena
     * localhost merujuk ke perangkat Android itu sendiri, bukan komputer.
     */
    private const val BASE_URL = "http://192.168.1.7:3000/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}