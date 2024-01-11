package kh.edu.rupp.ite.iteforecast.view.fragment

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kh.edu.rupp.ite.iteforecast.adapter.WeatherAdapter
import kh.edu.rupp.ite.iteforecast.data.WeatherRepository
import kh.edu.rupp.ite.iteforecast.databinding.FragmentLocationListBinding
import kh.edu.rupp.ite.iteforecast.model.WeatherResponse
import kh.edu.rupp.ite.iteforecast.network.WeatherService
import kh.edu.rupp.ite.iteforecast.viewmodel.WeatherViewModel
import kh.edu.rupp.ite.iteforecast.viewmodel.WeatherViewModelFactory

class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationListBinding
    private val viewModel: WeatherViewModel by activityViewModels {
        WeatherViewModelFactory(WeatherRepository(WeatherService.apiService))
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: WeatherAdapter
    private var isQuerySubmitted = false
    private val searchedCities = mutableSetOf<String>()
    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        searchView = binding.searchView
        adapter = WeatherAdapter()

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        restoreStateFromSharedPrefs()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submit
                query?.let {
                    // Check if the city has already been searched
                    if (searchedCities.contains(query)) {
                        // Handle the case where the city has already been searched
                        showToast("City '$query' has already been added.")
                    } else {
                        // Add the city to the set to keep track of searched cities
                        searchedCities.add(query)
                        viewModel.getWeather(it)
                        showToast("City '$query' has been added.")
                    }
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle text change if needed
                return true
            }
        })

        viewModel.weatherData.observe(viewLifecycleOwner, Observer { weatherResponse ->
            adapter.updateData(weatherResponse)
        })

    }

    private fun restoreStateFromSharedPrefs() {
        // Restore adapter data here
        val weatherDataJson = sharedPreferences.getString("weather_data", null)
        if (weatherDataJson != null) {
            val weatherData = Gson().fromJson(weatherDataJson, Array<WeatherResponse>::class.java)
            adapter.updateData(weatherData.toList())
        }
    }

    private fun saveStateToSharedPrefs() {
        with(sharedPreferences.edit()) {

            // Save adapter data here
            val weatherDataJson = Gson().toJson(adapter.weatherList)
            putString("weather_data", weatherDataJson)

            apply()
        }
    }

    override fun onPause() {
        super.onPause()
        saveStateToSharedPrefs()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


}