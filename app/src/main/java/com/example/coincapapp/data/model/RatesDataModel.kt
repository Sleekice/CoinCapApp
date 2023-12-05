package com.example.coincapapp.data.model


import com.google.gson.annotations.SerializedName

data class RatesDataModel(
    @SerializedName("data")
    val `data`: List<DataModel?>? = listOf(),
    @SerializedName("timestamp")
    val timestamp: Long? = 0
)

