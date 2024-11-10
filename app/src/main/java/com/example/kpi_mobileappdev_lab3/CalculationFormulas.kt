package com.example.kpi_mobileappdev_lab3

import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sqrt

fun normalPowerDistributionLaw(
    p: Double,
    averageDailyCapacity: Double,
    standardDeviation: Double
): Double {
    val coefficient = 1 / (standardDeviation * sqrt(2 * Math.PI))
    val exponent = (p - averageDailyCapacity).pow(2.0)/(2 * standardDeviation.pow(2.0))
    return coefficient * exp(-exponent)
}

fun gaussLegendreIntegration(
    averageDailyCapacity: Double,
    standardDeviation: Double,
    capacityRange: Pair<Double, Double>
): Double {
    val t: DoubleArray = doubleArrayOf(-0.90617985, -0.53846931, 0.0, 0.53846931, 0.90617985)
    val coefA: DoubleArray = doubleArrayOf(0.23692688, 0.47862868, 0.56888889, 0.47862868, 0.23692688)

    var integrationResult = 0.0

    for (i in 0 until 5) {
        val x = 0.5 * (capacityRange.second + capacityRange.first) +
                0.5 * (capacityRange.second - capacityRange.first) * t[i]
        val y = normalPowerDistributionLaw(x, averageDailyCapacity, standardDeviation)
        val coefC = 0.5 * (capacityRange.second - capacityRange.first) * coefA[i]
        integrationResult += coefC * y
    }

    return integrationResult
}
