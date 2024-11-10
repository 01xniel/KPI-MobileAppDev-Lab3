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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.example.kpi_mobileappdev_lab3.CalculatorInputFields
import com.example.kpi_mobileappdev_lab3.screens.components.ButtonComponent
import com.example.kpi_mobileappdev_lab3.screens.components.HeaderTextComponent
import com.example.kpi_mobileappdev_lab3.screens.components.InputFieldComponent
import com.example.kpi_mobileappdev_lab3.screens.components.MainTextComponent

fun isInputValid(value: String): Boolean {
    return if (value.isEmpty()) {
        true
    } else if (value.all { it.isDigit() || it == '.' }) {
        value.count { it == '.' } < 2
    } else {
        false
    }
}

@Composable
fun CalculatorScreen(
    toResultScreen: (route: String) -> Unit,
) {

    var calcInputFields by rememberSaveable { mutableStateOf(CalculatorInputFields()) }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .verticalScroll(rememberScrollState())
    ) {
        Column (
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            HeaderTextComponent("Калькулятор")
            MainTextComponent(
                text = AnnotatedString("Калькулятор розрахунку прибутку сонячних електростанцій з " +
                        "встановленою системою прогнозування сонячної потужності."),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column (
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InputFieldComponent(
                inputFieldValue = calcInputFields.averageDailyCapacity.first,
                inputFieldLabelText = calcInputFields.averageDailyCapacity.second,
                updateInputFieldValue = { newInputFieldValue ->
                    if (isInputValid(newInputFieldValue)) {
                        val updatedAverageDailyCapacity = calcInputFields.averageDailyCapacity.copy(
                            first = newInputFieldValue
                        )
                        calcInputFields = calcInputFields.copy(
                            averageDailyCapacity = updatedAverageDailyCapacity
                        )
                    }
                }
            )
            InputFieldComponent(
                inputFieldValue = calcInputFields.electricityCost.first,
                inputFieldLabelText = calcInputFields.electricityCost.second,
                updateInputFieldValue = { newInputFieldValue ->
                    if (isInputValid(newInputFieldValue)) {
                        val updatedElectricityCost = calcInputFields.electricityCost.copy(
                            first = newInputFieldValue
                        )
                        calcInputFields = calcInputFields.copy(
                            electricityCost = updatedElectricityCost
                        )
                    }
                }
            )
            InputFieldComponent(
                inputFieldValue = calcInputFields.standardDeviation.first,
                inputFieldLabelText = calcInputFields.standardDeviation.second,
                updateInputFieldValue = { newInputFieldValue ->
                    if (isInputValid(newInputFieldValue)) {
                        val updatedStandardDeviation = calcInputFields.standardDeviation.copy(
                            first = newInputFieldValue
                        )
                        calcInputFields = calcInputFields.copy(
                            standardDeviation = updatedStandardDeviation
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        ButtonComponent(
            buttonText = "Розрахувати",
            onClickAction = {
                toResultScreen(
                    "res-calc/" + calcInputFields.averageDailyCapacity.first +
                        "/" + calcInputFields.electricityCost.first + "/" +
                        calcInputFields.standardDeviation.first
                )
            }
        )
    }

}