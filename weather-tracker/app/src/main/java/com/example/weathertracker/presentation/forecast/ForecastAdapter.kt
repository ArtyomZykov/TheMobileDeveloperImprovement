package com.example.weathertracker.presentation.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertracker.R
import com.example.weathertracker.databinding.ItemForecastBinding
import com.example.weathertracker.domain.model.DailyForecast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ForecastAdapter : ListAdapter<DailyForecast, ForecastAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemForecastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemForecastBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("EEEE, MMM d", Locale.getDefault())

        fun bind(forecast: DailyForecast) {
            binding.apply {
                dateText.text = dateFormat.format(Date(forecast.date * 1000))
                temperatureText.text = itemView.context.getString(
                    R.string.temperature_range_format,
                    forecast.maxTemp,
                    forecast.minTemp
                )
                descriptionText.text = forecast.description
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<DailyForecast>() {
        override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
            return oldItem == newItem
        }
    }
} 