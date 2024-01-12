package kh.edu.rupp.ite.iteforecast.adapter

import android.icu.text.SimpleDateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kh.edu.rupp.ite.iteforecast.R
import kh.edu.rupp.ite.iteforecast.model.Hour
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale

class HourlyAdapter(private val hourlyDataList: List<Hour>) :
    RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {

    class HourlyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hourlyShortDateText: TextView = itemView.findViewById(R.id.hourlyShortDateText)
        val hourlyWeatherIcon: ImageView = itemView.findViewById(R.id.hourlyWeatherIcon)
        val hourlyTempText: TextView = itemView.findViewById(R.id.hourlyTempText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_hourly_forecast, parent, false)
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
