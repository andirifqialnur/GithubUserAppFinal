package com.aran.githubapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Config {
    fun getService(): Service {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofit.create(Service::class.java)
    }
}