package com.example.kpi_mobileappdev_lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kpi_mobileappdev_lab3.screens.CalculatorScreen
import com.example.kpi_mobileappdev_lab3.screens.ResultScreen
import com.example.kpi_mobileappdev_lab3.ui.theme.KPIMobileAppDevLab3Theme

@Composable
fun NavApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = CalculatorRoute,
    ) {
        composable(CalculatorRoute) {
            CalculatorScreen(
                toResultScreen = { route ->
                    navController.navigate(route)
                }
            )
        }
        composable(
            route = CalculatorResultRoute,
            arguments = listOf(
                navArgument("capacity") { type = NavType.FloatType },
                navArgument("cost") { type = NavType.FloatType },
                navArgument("deviation") { type = NavType.FloatType }
            )
        ) {
            ResultScreen(
                averageDailyCapacity = it.arguments?.getFloat("capacity")!!.toDouble(),
                electricityCost = it.arguments?.getFloat("cost")!!.toDouble(),
                standardDeviation = it.arguments?.getFloat("deviation")!!.toDouble(),
                backToCalc = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                }
            )
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KPIMobileAppDevLab3Theme {
                NavApp()
            }
        }
    }
}
