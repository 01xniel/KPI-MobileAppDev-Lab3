package com.example.kpi_mobileappdev_lab3

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CalculatorInputFields(
    val averageDailyCapacity: Pair<String, String> = Pair("5.0", "Середньодобова потужність (МВт)"),
    val electricityCost: Pair<String, String> = Pair("7.0", "Вартість електроенергії (грн/кВт⋅год)"),
    val standardDeviation: Pair<String, String> = Pair("1.0", "Середньоквадратичне відхилення (МВт)")
) : Parcelable
