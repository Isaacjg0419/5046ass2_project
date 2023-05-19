package com.example.a5046ass2_project.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.a5046ass2_project.map.MapsActivity
import com.example.a5046ass2_project.R
import com.example.a5046ass2_project.home.weather.City
import com.example.a5046ass2_project.databinding.FragmentHomeBinding
import kotlin.math.roundToInt

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var weatherViewModel: WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory()
        )[WeatherViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        weatherViewModel.getWeatherInfo()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchProperty.setOnClickListener {
            startActivity(Intent(requireActivity(), MapsActivity::class.java))
        }
        weatherViewModel.getCity.observe(viewLifecycleOwner) { city: City ->
            handleWeatherInfo(city)
        }
    }

    private fun handleWeatherInfo(city: City) {
        when (city.weather[0].description) {
            "clear sky", "mist" -> {
                Glide.with(requireActivity())
                    .load(R.drawable.clouds)
                    .into(binding.image)
            }

            "haze", "overcast clouds" -> {
                Glide.with(requireActivity())
                    .load(R.drawable.haze)
                    .into(binding.image)
            }

            else -> {
                Glide.with(requireActivity())
                    .load(R.drawable.rain)
                    .into(binding.image)
            }
        }

        binding.description.text = city.weather[0].description
        binding.name.text = city.name
        binding.degree.text = city.wind.deg.toString()
        binding.speed.text = city.wind.speed.toString()
        binding.temp.text =
            (((city.main.temp - 273) * 100.0).roundToInt() / 100.0).toString()
        binding.humidity.text = city.main.humidity.toString()

    }

}