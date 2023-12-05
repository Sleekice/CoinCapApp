package com.example.coincapapp.data.repository

import com.example.coincapapp.data.model.DataModel
import com.example.coincapapp.data.model.RatesDataModel
import com.example.coincapapp.data.remote.ApiEndpoint
import javax.inject.Inject


class RepositoryImpl @Inject constructor(val apiRequest: ApiEndpoint) :Repository{


    override suspend fun getRates(): RatesDataModel {
        return apiRequest.getRates()    }

    override suspend fun getRatesById(id: String): DataModel? {
        val findRateById = apiRequest.getRatesById(id)
        return findRateById.data?.find { it?.id == id }
    }
}

