package com.techclusterdesignwala.institute

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.techclusterdesignwala.institute.ui.navigation.AppNavigation
import com.techclusterdesignwala.institute.ui.theme.TechClusterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TechClusterTheme {
                AppNavigation()
            }
        }
    }
}
