package com.example.weather

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.model.WeatherData
import com.example.weather.model.WeatherInterface
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

const val TAG = "MainActivity"
const val API_KEY = "abca655c8fb6c771b90146dd2e747976"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData("30082,us", "metric", API_KEY)
    }

    private fun getData(zip: String, units: String, app_id: String) {
        WeatherInterface.initRetrofit().getApi(zip, units, app_id)
            .enqueue(object : Callback<WeatherData> {
                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    Log.d(TAG, "onFailure: fail to get data")
                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    if (response.isSuccessful) {
                        val weatherData = response.body()
                        binding.tvTemp.text = weatherData?.main?.temp.toString() + "°"
                        binding.tvCity.text = weatherData?.name.toString() + ","
                        binding.tvCountry.text = weatherData?.sys?.country.toString()
                        binding.tvDescription.text = weatherData?.weather?.get(0)?.description.toString()
                    }
                }
            })
    }

    /*val DataBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(WeatherInterface::class.java)

    val data = DataBuilder.getData()

    data.enqueue(object : Callback<WeatherData?>
    {
        override fun onResponse(call: Call<WeatherData?>, response: Response<WeatherData?>) {
            if (response.isSuccessful) {
                val weatherData = response.body()
                binding.tvTemp.text = weatherData?.main?.temp.toString() + "°"
                binding.tvCity.text = weatherData?.name.toString() + ","
                binding.tvCountry.text = weatherData?.sys?.country.toString()
                binding.tvDescription.text = weatherData?.weather?.get(0)?.description.toString()
            }
        }

        override fun onFailure(call: Call<WeatherData?>, t: Throwable) {
            TODO("Not yet implemented")
        }
    })*/
}

