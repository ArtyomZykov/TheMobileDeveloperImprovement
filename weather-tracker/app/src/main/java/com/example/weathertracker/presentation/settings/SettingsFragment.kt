package com.example.weathertracker.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.weathertracker.R
import com.example.weathertracker.databinding.FragmentSettingsBinding
import com.example.weathertracker.domain.repository.TemperatureUnit
import com.google.android.material.slider.Slider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeSettings()
    }

    private fun setupViews() {
        binding.apply {
            temperatureUnitGroup.setOnCheckedChangeListener { _, checkedId ->
                val unit = when (checkedId) {
                    R.id.celsiusRadio -> TemperatureUnit.CELSIUS
                    R.id.fahrenheitRadio -> TemperatureUnit.FAHRENHEIT
                    else -> return@setOnCheckedChangeListener
                }
                viewModel.setTemperatureUnit(unit)
                showSettingsSaved()
            }

            updateIntervalSlider.addOnChangeListener { _, value, fromUser ->
                if (fromUser) {
                    updateIntervalText.text = getString(
                        R.string.settings_update_interval_format,
                        value.toLong()
                    )
                }
            }

            updateIntervalSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: Slider) {}

                override fun onStopTrackingTouch(slider: Slider) {
                    viewModel.setUpdateInterval(slider.value.toLong())
                    showSettingsSaved()
                }
            })
        }
    }

    private fun observeSettings() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.temperatureUnit.collect { unit ->
                        binding.temperatureUnitGroup.check(
                            when (unit) {
                                TemperatureUnit.CELSIUS -> R.id.celsiusRadio
                                TemperatureUnit.FAHRENHEIT -> R.id.fahrenheitRadio
                            }
                        )
                    }
                }

                launch {
                    viewModel.updateInterval.collect { interval ->
                        binding.apply {
                            updateIntervalSlider.value = interval.toFloat()
                            updateIntervalText.text = getString(
                                R.string.settings_update_interval_format,
                                interval
                            )
                        }
                    }
                }
            }
        }
    }

    private fun showSettingsSaved() {
        Toast.makeText(requireContext(), R.string.settings_saved, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 