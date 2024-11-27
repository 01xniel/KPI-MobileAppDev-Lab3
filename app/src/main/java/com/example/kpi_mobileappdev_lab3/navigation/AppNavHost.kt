package com.example.kpi_mobileappdev_lab3.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kpi_mobileappdev_lab3.screens.CalcInputScreen
import com.example.kpi_mobileappdev_lab3.screens.CalcResultScreen

@Composable
fun AppNavHost() {
    // ініціалізація навігаційного контролера
    val navController = rememberNavController()

    // визначення маршрутизатора для навігації
    NavHost(
        navController = navController,
        startDestination = CalcInputRoute,
    ) {
        // екран вводу параметрів калькулятора
        composable(CalcInputRoute) {
            CalcInputScreen(
                toCalcResultScreen = { route ->
                    navController.navigate(route)
                }
            )
        }
        // екран результатів калькулятора
        composable(
            route = CalcResultRoute,
            arguments = listOf(
                navArgument("profit") { type = NavType.StringType },
                navArgument("electricityNoImbalance") { type = NavType.StringType },
                navArgument("penalty") { type = NavType.StringType },
                navArgument("electricityImbalance") { type = NavType.StringType },
                navArgument("netProfit") { type = NavType.StringType }
            )
        ) {
            CalcResultScreen(
                profit = it.arguments?.getString("profit")!!,
                electricityNoImbalance = it.arguments?.getString("electricityNoImbalance")!!,
                penalty = it.arguments?.getString("penalty")!!,
                electricityImbalance = it.arguments?.getString("electricityImbalance")!!,
                netProfit = it.arguments?.getString("netProfit")!!,
                toInputScreen = {
                    // повернення до екрану вводу параметрів
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                }
            )
        }
    }
}
