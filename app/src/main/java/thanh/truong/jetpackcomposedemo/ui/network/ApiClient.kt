package thanh.truong.jetpackcomposedemo.ui.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    fun getSimpleClient(url: String): Retrofit = Retrofit.Builder().apply {
        baseUrl(url)
        addConverterFactory(GsonConverterFactory.create())
        client(OkHttpClient.Builder().build())

    }.build()

    fun getDummyService() = getSimpleClient("https://dummyapi.io").create(DummyService::class.java)
}