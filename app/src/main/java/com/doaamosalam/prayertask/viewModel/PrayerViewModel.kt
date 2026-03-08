package com.doaamosalam.prayertask.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doaamosalam.domain.useCase.GetPrayerTimesUseCase
import com.doaamosalam.domain.util.Resource
import com.doaamosalam.prayertask.util.PrayerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class PrayerViewModel @Inject constructor(
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PrayerUiState())
    val uiState: StateFlow<PrayerUiState> = _uiState.asStateFlow()

    fun fetchPrayerTimes(city: String, country: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = getPrayerTimesUseCase(city, country)) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            prayerTimes = result.data,
                            errorMessage = null
                        )
                    }
                }
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.exception?.localizedMessage ?: "Unknown error"
                        )
                    }
                }
                else -> Unit
            }
        }
    }
}