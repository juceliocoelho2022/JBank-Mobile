package com.jucelio.jbankmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jucelio.jbankmobile.core.navigation.JBankNavHost
import com.jucelio.jbankmobile.ui.JBankApp
import com.jucelio.jbankmobile.ui.theme.JBankTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JBankTheme {
                JBankNavHost()
            }
        }
    }
}