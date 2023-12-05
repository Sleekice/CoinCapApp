import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.coincapapp.R
import com.example.coincapapp.data.model.RatesDataModel
import com.example.coincapapp.ui.ratesdetail.RatesDetailViewModel
import com.example.coincapapp.ui.ratesdetail.timestampToDateString
import com.example.coincapapp.ui.ratesrow.RatesRowViewModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun RatesRowScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<RatesRowViewModel>()
    val rates by viewModel.rates.collectAsState(null)


    DisposableEffect(Unit) {
        viewModel.getRates()
        onDispose { /* Cleanup if needed */ }
    }

        Text(
            text = "Today's Rate",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        RatesRow(ratesDataModel = rates, navController = navController)
    }


@Composable
fun RatesRow(ratesDataModel: RatesDataModel?,navController: NavHostController) {
    val viewModel = hiltViewModel<RatesDetailViewModel>()


    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight() // Use this to prevent vertical scrolling
            .padding(8.dp)
            .background(Color.Blue),
        horizontalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        ratesDataModel?.data?.forEach { dataModel ->
            val rateUsdString: String? = dataModel?.rateUsd

            val formattedRateUsd = if (!rateUsdString.isNullOrBlank()) {
                val rateUsd = rateUsdString.toFloatOrNull() ?: 0.0
                String.format("%.2f", rateUsd)
            } else {
                "N/A" // or any default value you prefer
            }

            item {
                Card(
                    modifier = Modifier
                        .width(180.dp)
                        .height(200.dp)
                        .padding(8.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(corner = CornerSize(16.dp)))
                        .clickable {
                            dataModel?.id?.let { id ->
                                navController.navigate("details/$id")
                            }
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${dataModel?.currencySymbol}",
                                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "\n${dataModel?.symbol}",
                                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            )
                        }

                        Text(
                            text = "1 USD/$formattedRateUsd ${dataModel?.symbol}",
                            style = TextStyle(fontSize = 16.sp)
                        )

                        Divider(
                            color = Color.Black,
                            thickness = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )


                        Text(
                            text = "id: ${dataModel?.id}",
                            style = TextStyle(fontSize = 16.sp, fontStyle = FontStyle.Italic)
                        )
                    }
                }
            }
        }
    }
}
