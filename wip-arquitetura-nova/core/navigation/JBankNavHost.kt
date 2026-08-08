package com.jucelio.jbankmobile.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jucelio.jbankmobile.feature.auth.presentation.screen.LoginRoute

@Composable
fun JBankNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {

            LoginRoute(

                onNavigateHome = {

                    navController.navigate("home")

                }

            )

        }

        composable("home") {

            // temporário

        }

    }

}