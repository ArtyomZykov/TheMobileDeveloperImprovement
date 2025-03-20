package com.example.weathertracker.presentation.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertracker.R
import com.example.weathertracker.databinding.ItemForecastBinding
import com.example.weathertracker.domain.model.DailyForecastEntity
import com.example.weathertracker.util.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class ForecastAdapter : ListAdapter<DailyForecastEntity, ForecastAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemForecastBinding.inflate(
            /* inflater = */        LayoutInflater.from(parent.context),
            /* parent = */          parent,
            /* attachToParent = */  false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemForecastBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(forecast: DailyForecastEntity) {
            binding.apply {
                dateText.text = Timestamp.convertUnixTimestampToDate(forecast.date)
                temperatureText.text = itemView.context.getString(
                    R.string.temperature_range_format,
                    forecast.maxTemp,
                    forecast.minTemp
                )
                descriptionText.text = forecast.description
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<DailyForecastEntity>() {
        override fun areItemsTheSame(oldItem: DailyForecastEntity, newItem: DailyForecastEntity): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: DailyForecastEntity, newItem: DailyForecastEntity): Boolean {
            return oldItem == newItem
        }
    }
}
