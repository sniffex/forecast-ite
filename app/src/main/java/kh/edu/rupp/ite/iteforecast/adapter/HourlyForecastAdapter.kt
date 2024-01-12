package kh.edu.rupp.ite.iteforecast.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.iteforecast.R
import kh.edu.rupp.ite.iteforecast.model.Hour

class HourlyForecastAdapter(var hourlyForecast: List<Hour>) : RecyclerView.Adapter<HourlyForecastAdapter.HourlyForecastViewHolder>() {

    class HourlyForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val forecastTextView: TextView = itemView.findViewById(R.id.hourlyShortDateText)
        val forecastTempTextView: TextView = itemView.findViewById(R.id.hourlyTempText)
        val forecastIconImageView: ImageView = itemView.findViewById(R.id.hourlyWeatherIcon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForecastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_hourly_forecast, parent, false)
        return HourlyForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourlyForecastViewHolder, position: Int) {
        val hour = hourlyForecast[position]

        holder.forecastTextView.text = hour.time
        holder.forecastTempTextView.text = "${hour.temp_c}"
        Log.d("HourlyForecastAdapter", "Binding data at position $position: ${hour.time}, ${hour.temp_c}°C")
        // Load weather icon using Glide
//        holder.forecastIconImageView.setImageURI(hour.condition.icon)
    }


    override fun getItemCount(): Int {
        return hourlyForecast.size
    }

}