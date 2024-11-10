package com.example.kpi_mobileappdev_lab3.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputFieldComponent(
    inputFieldLabelText: String,
    inputFieldValue: String,
    updateInputFieldValue: (newInputFieldValue: String) -> Unit
) {
    OutlinedTextField(
        value = inputFieldValue,
        onValueChange = { newInputFieldValue -> updateInputFieldValue(newInputFieldValue) },
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        textStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        ),
        label = {
            Text(
                text = inputFieldLabelText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier.fillMaxWidth(0.8f)
    )
}