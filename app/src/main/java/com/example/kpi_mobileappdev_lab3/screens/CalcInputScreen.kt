package com.example.kpi_mobileappdev_lab3.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.example.kpi_mobileappdev_lab3.CalcParameters
import com.example.kpi_mobileappdev_lab3.styledcomponents.CustomButton
import com.example.kpi_mobileappdev_lab3.styledcomponents.HeaderText
import com.example.kpi_mobileappdev_lab3.styledcomponents.CustomTextField
import com.example.kpi_mobileappdev_lab3.styledcomponents.BodyText

// функція перевірки коректності введеного значення в текстовому полі
fun isNewValueValid(value: String): Boolean {
    return if (value.isEmpty()) {
        true
    } else if (value.all { it.isDigit() || it == '.' }) {
        value.count { it == '.' } < 2
    } else {
        false
    }
}

@Composable
fun CalcInputScreen(
    toCalcResultScreen: (route: String) -> Unit,
) {
    // змінна для зберігання параметрів калькулятора
    var calcParameters by rememberSaveable { mutableStateOf(CalcParameters()) }

    // назви параметрів
    val textFieldLabels = hashMapOf(
        "averageDailyCapacity" to "Середньодобова потужність (МВт)",
        "electricityCost" to "Вартість електроенергії (грн/кВт⋅год)",
        "standardDeviation" to "Середньоквадратичне відхилення (МВт)"
    )

    // поточний контекст застосунку
    val context = LocalContext.current

    // головний контейнер екрану для введення значень калькулятора
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .verticalScroll(rememberScrollState())
            .padding(vertical = 64.dp)
    ) {
        HeaderText(
            text = "Калькулятор",
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        BodyText(
            text = AnnotatedString("Калькулятор розрахунку прибутку сонячних електростанцій з " +
                    "встановленою системою прогнозування сонячної потужності"),
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column (
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomTextField(
                textFieldValue = calcParameters.averageDailyCapacity,
                textFieldLabel = textFieldLabels["averageDailyCapacity"]!!,
                updateTextFieldValue = { newValue ->
                    if (isNewValueValid(newValue)) {
                        calcParameters = calcParameters.copy(averageDailyCapacity = newValue)
                    }
                }
            )
            CustomTextField(
                textFieldValue = calcParameters.electricityCost,
                textFieldLabel = textFieldLabels["electricityCost"]!!,
                updateTextFieldValue = { newValue ->
                    if (isNewValueValid(newValue)) {
                        calcParameters = calcParameters.copy(electricityCost = newValue)
                    }
                }
            )
            CustomTextField(
                textFieldValue = calcParameters.standardDeviation,
                textFieldLabel = textFieldLabels["standardDeviation"]!!,
                updateTextFieldValue = { newValue ->
                    if (isNewValueValid(newValue)) {
                        calcParameters = calcParameters.copy(standardDeviation = newValue)
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        CustomButton(
            onClickAction = {
                if (calcParameters.areFieldsNotEmpty()) {
                    // якщо всі параметри заповнені, то виконуються обчислення, після чого
                    // здійснюється перехід до сторінки з результатами
                    val (profit, electricityNoImbalance, penalty,
                        electricityImbalance, netProfit) = calcParameters.evaluate()
                    toCalcResultScreen("result/$profit/$electricityNoImbalance/" +
                            "$penalty/$electricityImbalance/$netProfit")
                } else {
                    // якщо хоча б один параметр порожній, то виводиться
                    // відповідне повідомлення
                    Toast.makeText(
                        context,
                        "Усі параметри мають бути заповнені",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            buttonTextContent = "Розрахувати"
        )
    }
}
