package org.example.project
import MonteCarloSimulationPage
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            CryptoHomeScreen(navController)
        }
        composable("chart") {
            CryptoChartScreen(navController)
        }
        composable("MonteCarloSimulationPage") {
            MonteCarloSimulationPage(navController)
        }
    }
}
