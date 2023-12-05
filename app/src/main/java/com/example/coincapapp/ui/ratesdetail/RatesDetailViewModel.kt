package com.example.coincapapp.ui.ratesdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coincapapp.data.model.DataModel
import com.example.coincapapp.data.model.RatesDataModel
import com.example.coincapapp.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatesDetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _detail = MutableStateFlow<DataModel?>(null)
    val detail: StateFlow<DataModel?> = _detail


    private val _rates = MutableStateFlow<RatesDataModel>(RatesDataModel()) // Fix 1
    val rates: StateFlow<RatesDataModel> = _rates // Fix 2


    fun getDetailById(id: String) {
        viewModelScope.launch {
            // Fetch details based on the id
            val response = repository.getRatesById(id)
            _detail.value = response
        }
    }

    //To get TimeStamp on each id
    fun getRates(){
        viewModelScope.launch {
            val response = repository.getRates()
            if (response != null) {
                _rates.value = response
            } else {
                _rates.value = RatesDataModel()
            }
        }
    }
}

