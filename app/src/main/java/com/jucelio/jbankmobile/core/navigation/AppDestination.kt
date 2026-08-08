package com.jucelio.jbankmobile.core.navigation

sealed class AppDestination(
    val route: String
) {

    data object Login : AppDestination("login")

    data object Home : AppDestination("home")

}