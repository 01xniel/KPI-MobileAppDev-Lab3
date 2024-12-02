package com.example.kpi_mobileappdev_lab3

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sqrt

// клас, що представляє параметри калькулятора, а також містить методи для розрахунку
// прибутку сонячних електростанцій з встановленою системою прогнозування сонячної потужності
@Parcelize
data class CalcParameters(
    val averageDailyCapacity: String = "5.0",
    val electricityCost: String = "7.0",
    val standardDeviation: String = "1.0"
) : Parcelable {
    // функція, що перевіряє чи всі поля заповнені
    fun areFieldsNotEmpty(): Boolean {
        return averageDailyCapacity.isNotEmpty() && electricityCost.isNotEmpty() &&
                standardDeviation.isNotEmpty()
    }

    // нормальний закон розподілу потужності
    private fun normalPowerDistributionLaw(
        p: Double,
        averageDailyCapacity: Double,
        standardDeviation: Double
    ): Double {
        val normalizationFactor = 1 / (standardDeviation * sqrt(2 * Math.PI))
        val exponentTerm = (p - averageDailyCapacity).pow(2.0)/(2 * standardDeviation.pow(2.0))
        return normalizationFactor * exp(-exponentTerm)
    }

    // функція чисельного інтегрування за методом гаусса-лежандра
    private fun gaussLegendreIntegration(
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

    // функція округлення числа з заданою точністю
    private fun roundNum(number: Double, precision: Int): Double {
        return BigDecimal(number).setScale(precision, RoundingMode.HALF_UP).toDouble()
    }

    // функція розрахунку прибутку сонячних електростанцій з встановленою
    // системою прогнозування сонячної потужності
    fun evaluate(): List<Double> {
        // приведення вхідних даних калькулятора до типу Double
        val averageDailyCapacity: Double = averageDailyCapacity.toDouble()
        val electricityCost: Double = electricityCost.toDouble()
        val standardDeviation: Double = standardDeviation.toDouble()

        // похибка прогнозу
        val forecastError: Double = averageDailyCapacity * 0.05
        // діапазон потужності
        val capacityRange: Pair<Double, Double> = Pair(
            averageDailyCapacity - forecastError,
            averageDailyCapacity + forecastError
        )

        // відсоток електроенергії, що генерується без небалансів
        val electricityNoImbalanceRelativeVal: Double = roundNum(
            number = gaussLegendreIntegration(
                standardDeviation = standardDeviation,
                averageDailyCapacity = averageDailyCapacity,
                capacityRange = capacityRange
            ),
            precision = 2
        )
        // відсоток електроенергії, що генерується з небалансами
        val electricityImbalanceRelativeVal: Double = roundNum(
            number = 1 - electricityNoImbalanceRelativeVal,
            precision = 2
        )

        // кількість електроенергії, що генерується без небалансів (в МВт)
        val electricityNoImbalance: Double = roundNum(
            number = averageDailyCapacity * 24 * electricityNoImbalanceRelativeVal,
            precision = 1
        )
        // кількість електроенергії, що генерується з небалансами (в МВт)
        val electricityImbalance: Double = roundNum(
            number = averageDailyCapacity * 24 * electricityImbalanceRelativeVal,
            precision = 1
        )

        // дохід від генерації енергії без небалансів (в тисячах гривень)
        val profit: Double = roundNum(
            number = electricityNoImbalance * electricityCost,
            precision = 1
        )
        // штраф за генерацію енергії з небалансами (в тисячах гривень)
        val penalty: Double = roundNum(
            number = electricityImbalance * electricityCost,
            precision = 1
        )

        // прибуток / збитки сонячної електростанції
        val netProfit = roundNum(number = profit - penalty, precision = 1)

        return listOf(profit, electricityNoImbalance, penalty, electricityImbalance, netProfit)
    }
}
