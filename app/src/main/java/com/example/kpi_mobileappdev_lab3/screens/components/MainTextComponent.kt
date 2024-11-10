package com.example.kpi_mobileappdev_lab3.screens.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun MainTextComponent(text: AnnotatedString, modifier: Modifier = Modifier) {
    Text(
        text,
        fontSize = 16.sp,
        lineHeight = 25.sp,
        textAlign = TextAlign.Justify,
        modifier = modifier
    )
}