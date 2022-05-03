package com.example.weather.viewmodel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weather.R
import com.example.weather.databinding.FragmentGettingParametersBinding
import com.example.weather.view.MainActivity

class InputAdapter : AppCompatActivity()
{
    val TAG = "InputAdapter"
    private lateinit var binding: FragmentGettingParametersBinding
    internal lateinit var units : String
    internal lateinit var country : String
    private var zip = ""


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = FragmentGettingParametersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnCheckedChangeListener()

        passInputs()
    }

    private fun passInputs()
    {
        binding.btnUpdate.setOnClickListener {
            zip = binding.etZipCode.text.toString()
            if (binding.etZipCode.text.toString().isNotEmpty() )
            {
                Log.d(TAG, "passInputs: zip: $zip")
                Log.d(TAG, "passInputs: unit: $units")
                Log.d(TAG, "passInputs: country: $country")

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("zip", zip)
                intent.putExtra("units", units)
                intent.putExtra("country", country)
                startActivity(intent)

            }
            else
            {
                Toast.makeText(this, "Please enter a zip code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setOnCheckedChangeListener()
    {
        binding.rgUnits.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId)
            {
                R.id.rb_metric -> units = "metric"
                R.id.rb_imperial -> units = "imperial"
                R.id.rb_kelvin -> units = "default"
            }
        }
        binding.rgCountries.setOnCheckedChangeListener { _, checked ->
            when (checked)
            {
                R.id.rb_united_states -> country = "us"
                R.id.rb_canada -> country = "ca"
            }
        }
    }
}