package kh.edu.rupp.ite.iteforecast.adapter

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kh.edu.rupp.ite.iteforecast.databinding.ViewHolderDayForecastBinding
import kh.edu.rupp.ite.iteforecast.model.ForecastDay
import java.util.Locale

class DailyForecastAdapter(private val dailyForecastList: List<ForecastDay>) :
    RecyclerView.Adapter<DailyForecastAdapter.DailyForecastViewHolder>() {
   class DailyForecastViewHolder(binding: ViewHolderDayForecastBinding) :
       RecyclerView.ViewHolder(binding.root) {
       val timeDisplay: TextView = binding.timeDisplay
       val tempDisplay: TextView = binding.tempDisplay
       val imageDisplay: ImageView = binding.imageDisplay }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
            val view = ViewHolderDayForecastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return DailyForecastViewHolder(view)
        }

        override fun onBindViewHolder(
            holder: DailyForecastViewHolder,
            position: Int
        ) {
            val days = dailyForecastList[position]
            holder.timeDisplay.text = dateFormat(days.date)
            holder.tempDisplay.text = "${days.day.avgtemp_c}°C"
            val fullUrl = "https:${days.day.condition.icon}"
            Glide.with(holder.itemView.context)
                .load(fullUrl)
                .into(holder.imageDisplay)

        }

        private fun dateFormat(timeString: String): String {
            return try {
                SimpleDateFormat("dd-MM", Locale.getDefault()).format(
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(timeString)
                )
            } catch (e: Exception) {
                ""
            }
        }

        override fun getItemCount(): Int {
            return dailyForecastList.size
        }


}