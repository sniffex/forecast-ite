package kh.edu.rupp.ite.iteforecast.adapter

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kh.edu.rupp.ite.iteforecast.databinding.ViewHolderHourlyForecastBinding
import kh.edu.rupp.ite.iteforecast.model.Hour
import java.util.Locale

class HourlyAdapter(private val hourlyDataList: List<Hour>) :
    RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {

    class HourlyViewHolder(binding: ViewHolderHourlyForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val hourlyShortDateText: TextView = binding.hourlyShortDateText
        val hourlyWeatherIcon: ImageView = binding. hourlyWeatherIcon
        val hourlyTempText: TextView = binding.hourlyTempText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val view = ViewHolderHourlyForecastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HourlyViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val hourlyData = hourlyDataList[position]

        holder.hourlyShortDateText.text = timeFormat(hourlyData.time)
        // Load weather icon using Glide
        val fullUrl = "https:${hourlyData.condition.icon}"
        Glide.with(holder.itemView.context)
            .load(fullUrl)
            .into(holder.hourlyWeatherIcon)
        holder.hourlyTempText.text = "${hourlyData.temp_c}°C"


    }
    private fun timeFormat(timeString: String): String {
        return try {
            SimpleDateFormat("HH:mm", Locale.getDefault()).format(
                SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(timeString)
            )
        } catch (e: Exception) {
            ""
        }
    }


    override fun getItemCount(): Int {
        return hourlyDataList.size
    }
}
