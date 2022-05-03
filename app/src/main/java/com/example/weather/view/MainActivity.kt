package com.example.weather.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.weather.R
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.model.WeatherData
import com.example.weather.model.WeatherInterface
import com.example.weather.viewmodel.InputAdapter
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

const val TAG = "MainActivity"
const val API_KEY = "abca655c8fb6c771b90146dd2e747976"
var zipCode : String = ""
var country: String = ""
var units: String = ""
var fullzip: String = ""

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        zipCode = intent.getStringExtra("zip").toString()
        country = intent.getStringExtra("country").toString()
        units = intent.getStringExtra("units").toString()

        fullzip = "$zipCode,$country"

        Log.d(TAG, "onCreate: $fullzip, $units")

        getData(fullzip, units, API_KEY)
        SettingButon()
    }



    fun getData(zip: String, units: String, app_id: String) {
        WeatherInterface.initRetrofit().getApi(zip, units, app_id)
            .enqueue(object : Callback<WeatherData> {
                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    Log.d(TAG, "onFailure: fail to get data")
                }

                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "onResponse: ${response.body()}")
                        val weatherData = response.body()
                        binding.tvTemp.text = weatherData?.main?.temp.toString() + "°"+getUnits(units)
                        binding.tvCity.text = weatherData?.name.toString() + ","
                        binding.tvCountry.text = weatherData?.sys?.country.toString()
                        binding.tvDescription.text = weatherData?.weather?.get(0)?.description.toString()
                        weatherData?.main?.temp?.let { getBackground(getUnits(units), it) }
                    }
                }
            })
    }

    private fun getUnits(units: String): String {
        return when (units) {
            "metric" -> "C"
            "imperial" -> "F"
            else -> "K"
        }
    }

    private fun getBackground(units: String , temp: Double)
    {
        if (units == "C" && temp >= 21){
            binding.tempLayout.setBackgroundResource(R.color.Warm)
        }
        else if (units == "F" && temp >= 70){
            binding.tempLayout.setBackgroundResource(R.color.Warm)
            Log.d(TAG, "getBackground: $temp")
        }
        else if (units == "K" && temp >= 294){
            binding.tempLayout.setBackgroundResource(R.color.Warm)
        }
        else{
            binding.tempLayout.setBackgroundResource(R.color.Cool)
        }

    }
    private fun SettingButon(){
        binding.btnSettings.setOnClickListener {
            val intent = Intent(this, InputAdapter::class.java)
            startActivity(intent)
        }
    }
}

