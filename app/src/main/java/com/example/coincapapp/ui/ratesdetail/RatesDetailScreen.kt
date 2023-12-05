package com.example.coincapapp.ui.ratesdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coincapapp.data.model.DataModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RatesDetailScreen(id: String?) {
    val viewModel = hiltViewModel<RatesDetailViewModel>()
    val detail by viewModel.detail.collectAsState(null)

    val rates by viewModel.rates.collectAsState(null)


    DisposableEffect(Unit) {
        id?.let { viewModel.getDetailById(it) }
        viewModel.getRates()
        onDispose { /* Cleanup if needed */ }
    }

    // Replace this with your actual detail content
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopStart
    ) {
        if (detail != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Display rateUsd
                DetailItem("Rate (USD)", detail?.rateUsd ?: "")

                // Display other properties (symbol, type, etc.)
                DetailItem("Symbol", detail?.symbol ?: "")
                DetailItem("Type", detail?.type ?: "")
                DetailItem("Currency Symbol", detail?.currencySymbol ?: "")

                // Display timestamp at the bottom
                Text(
                    text = "Last Updated: ${rates?.timestamp?.let { timestampToDateString(it) } ?: ""}",
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.padding(top = 16.dp)
                )

                // New Text composable displaying the timestamp from the API
                Text(
                    text = "API Timestamp: ${rates?.timestamp?.let { timestampToDateStringFromApi(it) } ?: ""}",
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        } else {
            // Handle the case where details are not available
            Text(
                text = "Details not available",
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            color = Color.Gray
        )
        Text(
            text = value,
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}



fun timestampToDateString(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())
    val date = Date(timestamp)
    return dateFormat.format(date)
}


fun timestampToDateStringFromApi(timestamp: Long): String {
    // Convert the timestamp to seconds by dividing by 1000
    val timestampSeconds = timestamp / 1000
    val dateFormat = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())
    val date = Date(timestampSeconds * 1000)
    return dateFormat.format(date)
}