package com.example.weathertracker.presentation.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.weathertracker.R
import com.example.weathertracker.databinding.FragmentCurrentWeatherBinding
import com.example.weathertracker.domain.model.Weather
import com.example.weathertracker.presentation.common.UiState
import com.example.weathertracker.presentation.weather.CurrentWeatherViewModel.Action
import com.example.weathertracker.presentation.weather.CurrentWeatherViewModel.Event
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment() {

    private var _binding: FragmentCurrentWeatherBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CurrentWeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        setupListeners()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.handleAction(Action.OnPullToRefreshTriggered)
        }
        binding.forecastButton.setOnClickListener {
            viewModel.handleAction(Action.OnForecastButtonClicked)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch { viewModel.state.collect(::handleState) }
                launch { viewModel.event.collect(::handleEvent) }
            }
        }
    }

    private fun handleState(state: UiState<Weather>) = when (state) {
        UiState.Loading -> showLoading()
        is UiState.Error -> showError(state.message)
        is UiState.Success -> showWeather(state.data)
    }

    private fun handleEvent(event: Event) = when (event) {
        Event.OpenForecastScreen -> findNavController().navigate(
            CurrentWeatherFragmentDirections.actionCurrentWeatherToForecast()
        )

        Event.OpenSettingsScreen -> findNavController().navigate(
            CurrentWeatherFragmentDirections.actionCurrentWeatherToSettings()
        )

        Event.ShowSyncErrorView -> {
            Toast.makeText(requireContext(), "Synс error. Data may not be up to date", Toast.LENGTH_LONG).show()
        }

        Event.HideRefreshView -> {
            viewModel.onStateObserved()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    // region Ui
    private fun showLoading() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            temperatureText.visibility = View.INVISIBLE
            descriptionText.visibility = View.INVISIBLE
            forecastButton.visibility = View.INVISIBLE
        }
    }

    private fun setupMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_current_weather, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
                R.id.action_settings -> {
                    viewModel.handleAction(Action.OnSettingsClicked)
                    true
                }

                else -> false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun showWeather(weather: Weather) {
        binding.apply {
            progressBar.visibility = View.GONE
            swipeRefresh.isRefreshing = false
            temperatureText.apply {
                visibility = View.VISIBLE
                text = getString(R.string.temperature_format, weather.temperature)
            }
            descriptionText.apply {
                visibility = View.VISIBLE
                text = weather.description
            }
            forecastButton.visibility = View.VISIBLE
        }
    }

    private fun showError(message: String) {
        binding.apply {
            progressBar.visibility = View.GONE
            swipeRefresh.isRefreshing = false
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }
    }
    // endregion Ui
} 