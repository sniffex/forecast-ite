package kh.edu.rupp.ite.iteforecast.adapter

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kh.edu.rupp.ite.iteforecast.R
import kh.edu.rupp.ite.iteforecast.model.WeatherResponse


class WeatherAdapter: RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    public val weatherList: MutableList<WeatherResponse> = mutableListOf()

    fun updateData(newWeatherList: List<WeatherResponse>) {
        this.weatherList.clear()
        this.weatherList.addAll(newWeatherList)
        notifyDataSetChanged()
    }

//    fun updateData(newData: WeatherResponse) {
//        weatherList.add(newData)
//        notifyDataSetChanged()
//    }


    // Function to remove an item from the list
    fun removeItem(position: Int) {
        weatherList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_location_card, parent, false)
        return WeatherViewHolder(view)

    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weatherResponse = weatherList[position]
        holder.bind(weatherResponse)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(weatherResponse: WeatherResponse) {
            // Find views within the item layout
            val locationTextView = itemView.findViewById<TextView>(R.id.locationCity)
            val temperatureTextView = itemView.findViewById<TextView>(R.id.temperature)
            val weatherTextView = itemView.findViewById<TextView>(R.id.weatherDescription)
            val locationCountryTextView = itemView.findViewById<TextView>(R.id.locationCountry)
            val windSpeedTextView = itemView.findViewById<TextView>(R.id.windSpeed)
            val humidityTextView = itemView.findViewById<TextView>(R.id.humidityText)
            val weatherIconImageView = itemView.findViewById<ImageView>(R.id.weatherIcon)

            // Set values based on the WeatherResponse
            locationTextView.text = weatherResponse.location.name
            temperatureTextView.text = "${weatherResponse.current.temp_c}"
            weatherTextView.text = weatherResponse.current.condition.text
            locationCountryTextView.text = weatherResponse.location.country
            windSpeedTextView.text = "Wind: ${weatherResponse.current.wind_kph} km/h"
            humidityTextView.text = "Humidity: ${weatherResponse.current.humidity}%"

            // Load weather icon using Glide or any other image loading library
            // Example using Glide:
            weatherIconImageView.setImageURI(weatherResponse.current.condition.icon)


        }

        private fun ImageView.setImageURI(icon: String) {
            val fullUrl = "https:$icon"
            Glide.with(this)
                .load(fullUrl)
                .placeholder(R.drawable.ic_default_weather) // Default icon if loading fails
                .error(R.drawable.ic_default_weather) // Default icon if there's an error
                .into(this)
        }

    }

    // Function to set up swipe-to-delete
    fun setUpItemTouchHelper(recyclerView: RecyclerView) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            private val cornerRadius = 30f
            private val background: Drawable = createSwipeBackground()
            private val deleteIcon = ContextCompat.getDrawable(recyclerView.context, R.drawable.ic_delete)

            private fun createSwipeBackground(): Drawable {
                val shapeDrawable = ShapeDrawable()
                val roundRectShape = RoundRectShape(
                    floatArrayOf(
                        cornerRadius, cornerRadius,
                        cornerRadius, cornerRadius,
                        cornerRadius, cornerRadius,
                        cornerRadius, cornerRadius
                    ),
                    null,
                    null
                )
                shapeDrawable.shape = roundRectShape
                shapeDrawable.paint.color = Color.RED // Change color as needed
                return shapeDrawable
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Handle swipe-to-delete action
                val position = viewHolder.adapterPosition
                removeItem(position)
            }

            override fun onChildDraw(
                canvas: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView

                // Draw the rounded background
                background.setBounds(
                    itemView.right + dX.toInt(),
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
                background.draw(canvas)

                // Draw the delete icon
                val iconMargin = (itemView.height - deleteIcon?.intrinsicHeight!!) / 2
                val iconTop = itemView.top + (itemView.height - deleteIcon.intrinsicHeight) / 2
                val iconBottom = iconTop + deleteIcon.intrinsicHeight
                val iconLeft = itemView.right - iconMargin - deleteIcon.intrinsicWidth
                val iconRight = itemView.right - iconMargin
                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                deleteIcon.draw(canvas)

                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }




}