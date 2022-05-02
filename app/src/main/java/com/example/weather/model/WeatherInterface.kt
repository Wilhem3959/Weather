package com.example.weather.model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherInterface {

    @GET(END_POINT)
    fun getApi(
        @Query(ZIP) zip: String,
        @Query(UNITS) units: String,
        @Query(APP_ID) app_id: String
    ): Call<WeatherData>

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/"
        private const val END_POINT = "/data/2.5/weather"
        private const val ZIP = "zip"
        private const val UNITS = "units"
        private const val APP_ID = "appid"

        fun initRetrofit(): WeatherInterface {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherInterface::class.java)
        }
    }

}
