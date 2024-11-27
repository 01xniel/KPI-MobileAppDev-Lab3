package com.example.kpi_mobileappdev_lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.kpi_mobileappdev_lab3.navigation.AppNavHost
import com.example.kpi_mobileappdev_lab3.ui.theme.KPIMobileAppDevLab3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KPIMobileAppDevLab3Theme {
                AppNavHost()
            }
        }
    }
}
