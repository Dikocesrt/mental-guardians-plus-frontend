package com.example.mentalguardians.data.retrofit

import com.example.mentalguardians.utils.AuthInterceptor
import com.example.mentalguardians.utils.UserPreferences
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient(private val userPreferences: UserPreferences) {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://dikoalterra.tech/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(userPreferences)) // Mengoper UserPreferences ke interceptor
            .build())
        .build()

    val retrofitService: ApiService = retrofit.create(ApiService::class.java)
}