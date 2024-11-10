package com.example.kpi_mobileappdev_lab3.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kpi_mobileappdev_lab3.gaussLegendreIntegration
import com.example.kpi_mobileappdev_lab3.screens.components.ButtonComponent
import com.example.kpi_mobileappdev_lab3.screens.components.HeaderTextComponent
import com.example.kpi_mobileappdev_lab3.screens.components.MainTextComponent
import java.math.BigDecimal
import java.math.RoundingMode

fun roundNumber(number: Double, precision: Int): Double {
    return BigDecimal(number).setScale(precision, RoundingMode.HALF_UP).toDouble()
}

@Composable
fun ResultScreen(
    averageDailyCapacity: Double,
    electricityCost: Double,
    standardDeviation: Double,
    backToCalc: () -> Unit
) {

    val forecastError: Double = averageDailyCapacity * 0.05
    val capacityRange: Pair<Double, Double> = Pair(
        averageDailyCapacity - forecastError,
        averageDailyCapacity + forecastError
    )

    val electricityNoImbalanceRelativeVal: Double = roundNumber(
        number = gaussLegendreIntegration(
            standardDeviation = standardDeviation,
            averageDailyCapacity = averageDailyCapacity,
            capacityRange = capacityRange
        ),
        precision = 2
    )
    val electricityImbalanceRelativeVal: Double = roundNumber(
        number = 1 - electricityNoImbalanceRelativeVal,
        precision = 2
    )

    val electricityNoImbalance: Double = roundNumber(
        number = averageDailyCapacity * 24 * electricityNoImbalanceRelativeVal,
        precision = 1
    )
    val electricityImbalance: Double = roundNumber(
        number = averageDailyCapacity * 24 * electricityImbalanceRelativeVal,
        precision = 1
    )

    val profit: Double = roundNumber(number = electricityNoImbalance * electricityCost, precision = 1)
    val penalty: Double = roundNumber(number = electricityImbalance * electricityCost, precision = 1)

    val netProfit = roundNumber(number = profit - penalty, precision = 1)

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .verticalScroll(rememberScrollState())
    ) {
        HeaderTextComponent(
            text = "Результати",
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Column (
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            MainTextComponent(
                text = AnnotatedString.Builder().apply {
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                    append("$profit тис. грн.")
                    pop()
                    append(" - дохід від генерації енергії без небалансів (")
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                    append("$electricityNoImbalance МВт⋅год")
                    pop()
                    append(").")
                }.toAnnotatedString()
            )
            MainTextComponent(
                text = AnnotatedString.Builder().apply {
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                    append("$penalty тис. грн.")
                    pop()
                    append(" - штраф за генерацію енергії з небалансами (")
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                    append("$electricityImbalance МВт⋅год")
                    pop()
                    append(").")
                }.toAnnotatedString()
            )
            Spacer(modifier = Modifier.height(6.dp))
            MainTextComponent(
                text = AnnotatedString.Builder().apply {
                    append(if (netProfit >= 0) "Прибуток: " else "Збитки: ")
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                    if (netProfit >= 0) {
                        append("$netProfit тис. грн.")
                    } else {
                        append(netProfit.toString().substring(1) + " тис. грн.")
                    }
                }.toAnnotatedString()
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        ButtonComponent(
            buttonText = "Назад",
            onClickAction = backToCalc
        )
    }
}