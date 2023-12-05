package com.example.coincapapp.data.repository

import com.example.coincapapp.data.model.DataModel
import com.example.coincapapp.data.model.RatesDataModel

interface Repository {

        suspend fun getRates(): RatesDataModel

        suspend fun getRatesById(id: String): DataModel?

}