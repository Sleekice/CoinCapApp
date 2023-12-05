package com.example.coincapapp.ui.ratesrow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coincapapp.data.model.RatesDataModel
import com.example.coincapapp.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatesRowViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _rates = MutableStateFlow<RatesDataModel?>(null)
    val rates: StateFlow<RatesDataModel?> = _rates

    fun getRates() {
        viewModelScope.launch {
            try {
                val ratesDataModel = repository.getRates()
                _rates.value = ratesDataModel
                } catch (e: Exception) {
                // Handle the exception
            }
        }
    }
}
