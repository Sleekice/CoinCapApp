package com.example.coincapapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coincapapp.ui.rates.RatesScreen
import com.example.coincapapp.ui.ratesdetail.RatesDetailScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "rates"
    ) {
        composable("rates") {
            RatesScreen(navController)
        }
        composable("details/{id}") { backStackEntry ->
            // Extract the id from the backStackEntry
            val id = backStackEntry.arguments?.getString("id")
            RatesDetailScreen(id = id.orEmpty())
        }
    }
}
