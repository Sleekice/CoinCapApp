package com.example.coincapapp.ui.rates

import RatesRowScreen
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.coincapapp.data.model.DataModel
import com.example.coincapapp.data.model.RatesDataModel
import java.util.Locale


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RatesScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<RatesScreenViewModel>()
    val rates by viewModel.rates.collectAsState(null)


    DisposableEffect(Unit) {
        viewModel.getRates()
        onDispose { /* Cleanup if needed */ }
    }



    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        stickyHeader {
            RatesRowScreen(navController = navController)

        }
        //item{ RatesRowScreen() }


        item {
            Divider(
                color = Color.Black,
                thickness = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }


        rates?.data?.forEach { ratesDataModel ->
            item {
                RatesCard(dataModel = ratesDataModel, navController = navController)
            }
        }

        item {
            Text(
                text = "Bottom Center Text",
                textAlign = TextAlign.Center,
                color = Color.Black,
                style = TextStyle(fontSize = 18.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}


@Composable
fun RatesCard(dataModel: DataModel?, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(corner = CornerSize(16.dp)))
            .background(Color.Gray)
            .clickable {
                dataModel?.id?.let { id ->
                    navController.navigate("details/$id")
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Currency Symbol",
                textAlign = TextAlign.Center,
                color = Color.Black,
                style = TextStyle(fontSize = 18.sp),
                modifier = Modifier.padding(8.dp)
            )

            // SYMBOL (in CAPS and BOLD)
            Text(
                text = dataModel?.symbol?.toUpperCase(Locale.ROOT) ?: "",
                textAlign = TextAlign.Center,
                color = Color.Black,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(8.dp)
            )

            // Divider
            Divider(
                color = Color.Black,
                thickness = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // Rate of the Currency Symbol to the dollar (rateUsd)
            Text(
                text = "Rate to USD: ${dataModel?.rateUsd ?: ""}",
                textAlign = TextAlign.Center,
                color = Color.Black,
                style = TextStyle(fontSize = 18.sp),
                modifier = Modifier.padding(8.dp)
            )

            // Divider
            Divider(
                color = Color.Black,
                thickness = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // Type (italics) and ID (italics)
            Text(
                text = "Type: ${dataModel?.type.orEmpty()}",
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                color = Color.Black,
                style = TextStyle(fontSize = 18.sp),
                modifier = Modifier.padding(8.dp)
            )

            Text(
                text = "ID: ${dataModel?.id.orEmpty()}",
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                color = Color.Black,
                style = TextStyle(fontSize = 18.sp),
                modifier = Modifier.padding(8.dp)
            )


        }
    }
}