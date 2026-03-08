package com.doaamosalam.prayertask.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doaamosalam.domain.useCase.GetNextPrayerUseCase
import com.doaamosalam.domain.useCase.GetPrayerTimesUseCase
import com.doaamosalam.domain.util.Resource
import com.doaamosalam.prayertask.util.PrayerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrayerViewModel @Inject constructor(
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
    private val getNextPrayerUseCase: GetNextPrayerUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PrayerUiState())
    val uiState: StateFlow<PrayerUiState> = _uiState.asStateFlow()

    fun fetchPrayerTimes(city: String, country: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = getPrayerTimesUseCase(city, country)) {

                is Resource.Success -> {

                    val prayerTimes = result.data!!

                    val nextPrayer = getNextPrayerUseCase(prayerTimes)

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            prayerTimes = prayerTimes,
                            nextPrayer = nextPrayer,
                            errorMessage = null
                        )
                    }
                }

                is Resource.Error -> {

                    Log.e("PrayerVM", "API failed, trying cache...")
                    val cached = getPrayerTimesUseCase(city, country) // Retry from DB
                    Log.e("PrayerVM", "Cached data: $cached")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            prayerTimes = cached.data,
                            errorMessage = result.exception?.localizedMessage ?: "Unknown error"
                        )
                    }
                }

                else -> Unit
            }
        }
    }
}