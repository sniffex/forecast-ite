package kh.edu.rupp.ite.iteforecast.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.iteforecast.adapter.WeatherAdapter
import kh.edu.rupp.ite.iteforecast.data.WeatherRepository
import kh.edu.rupp.ite.iteforecast.databinding.FragmentLocationListBinding
import kh.edu.rupp.ite.iteforecast.network.WeatherService
import kh.edu.rupp.ite.iteforecast.viewmodel.WeatherViewModel
import kh.edu.rupp.ite.iteforecast.model.WeatherResponse
import kh.edu.rupp.ite.iteforecast.viewmodel.WeatherViewModelFactory

class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationListBinding
    private val viewModel: WeatherViewModel by activityViewModels {
        WeatherViewModelFactory(WeatherRepository(WeatherService.apiService))
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: WeatherAdapter

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

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.getWeather(it)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("query", searchView.query.toString())
    }


}