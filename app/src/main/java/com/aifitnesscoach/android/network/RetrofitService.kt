package com.aifitnesscoach.android.network

import android.content.Context
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitService {

    // 🔹 Must NOT be private because WelcomeScreen uses it
    var BASE_URL = "https://ai-fitness-coach-backend-an8o.onrender.com/"

    private var apiService: ApiService? = null

    // 🔹 Change server at runtime
    fun changeBaseUrl(newUrl: String) {
        BASE_URL = newUrl
        apiService = null // recreate retrofit
    }

    fun getApiService(context: Context): ApiService {

        if (apiService != null) {
            return apiService!!
        }

        val tokenManager = TokenManager(context)

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(AuthInterceptor { tokenManager.getToken() })
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        return apiService!!
    }

    inline fun <reified T> handleRequest(
        response: Response<T>?,
        onSuccess: (T) -> Unit,
        onError: (T?) -> Unit
    ) {
        if (response == null) {
            onError(null)
            return
        }
        if (response.isSuccessful) {
            response.body()?.let { onSuccess(it) } ?: onError(null)
        } else {
            try {
                val errorBody = response.errorBody()?.string()
                val errorData: T? = errorBody?.let { Gson().fromJson(it, T::class.java) }
                onError(errorData)
            } catch (_: Exception) {
                onError(null)
            }
        }
    }
}