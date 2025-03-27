package com.example.weathertracker.presentation.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.weathertracker.R
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.weathertracker.databinding.FragmentForecastBinding
import com.example.weathertracker.domain.model.DailyForecastEntity
import com.example.weathertracker.presentation.common.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ForecastViewModel by viewModels()
    private val forecastAdapter = ForecastAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        binding.forecastList.adapter = forecastAdapter
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshForecast()
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

    private fun handleState(state: UiState<List<DailyForecastEntity>>) {
        when (state) {
            is UiState.Loading -> showLoading()
            is UiState.Success -> showForecast(state.data)
            is UiState.Error -> showError(state.message)
        }
    }

    private fun handleEvent(event: ForecastViewModel.Event) {
        when (event) {
            is ForecastViewModel.Event.ShowSyncErrorView -> showError("Sync error")
            ForecastViewModel.Event.HideRefreshView -> binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun showLoading() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            forecastList.visibility = View.INVISIBLE
        }
    }

    private fun showForecast(forecast: List<DailyForecastEntity>) {
        binding.apply {
            progressBar.visibility = View.GONE
            swipeRefresh.isRefreshing = false
            forecastList.visibility = View.VISIBLE
            forecastAdapter.submitList(forecast)
        }
    }

    private fun showError(message: String) {
        binding.apply {
            progressBar.visibility = View.GONE
            swipeRefresh.isRefreshing = false
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
