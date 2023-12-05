package com.example.coincapapp.data.remote

import com.example.coincapapp.data.model.DataModel
import com.example.coincapapp.data.model.RatesDataModel
import com.example.coincapapp.data.repository.Repository
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

interface ApiEndpoint {

    @GET(ApiDetails.RATES_ENDPOINT)
    suspend fun getRates(): RatesDataModel

    @GET(ApiDetails.RATES_ENDPOINT)
    suspend fun getRatesById(@Query("id") id: String): RatesDataModel



}


