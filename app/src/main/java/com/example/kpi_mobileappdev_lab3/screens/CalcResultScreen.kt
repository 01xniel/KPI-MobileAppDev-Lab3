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
import com.example.kpi_mobileappdev_lab3.styledcomponents.CustomButton
import com.example.kpi_mobileappdev_lab3.styledcomponents.HeaderText
import com.example.kpi_mobileappdev_lab3.styledcomponents.BodyText

@Composable
fun CalcResultScreen(
    profit: String,
    electricityNoImbalance: String,
    penalty: String,
    electricityImbalance: String,
    netProfit: String,
    toInputScreen: () -> Unit
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .verticalScroll(rememberScrollState())
    ) {
        HeaderText(
            text = "Результати",
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column (
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            BodyText(
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
            BodyText(
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
            BodyText(
                text = AnnotatedString.Builder().apply {
                    val isNetProfitNegative = netProfit[0] == '-'
                    append(if (isNetProfitNegative) "Збитки: " else "Прибуток: ")
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                    if (isNetProfitNegative) {
                        append(netProfit.substring(1))
                    } else {
                        append(netProfit)
                    }
                    append(" тис. грн.")
                }.toAnnotatedString()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        CustomButton(
            buttonTextContent = "Назад",
            onClickAction = toInputScreen
        )
    }
}