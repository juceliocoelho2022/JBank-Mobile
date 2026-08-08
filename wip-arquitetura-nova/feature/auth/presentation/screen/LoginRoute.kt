package com.jucelio.jbankmobile.feature.auth.presentation.screen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jucelio.jbankmobile.feature.auth.presentation.effect.LoginEffect
import com.jucelio.jbankmobile.feature.auth.presentation.viewmodel.LoginViewModel

@Composable
fun LoginRoute(
    onNavigateHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {

        viewModel.effect.collect { effect ->

            when (effect) {

                LoginEffect.NavigateHome ->
                    onNavigateHome()

                is LoginEffect.ShowError -> {

                    // Snackbar
                    // Dialog
                    // Toast
                }

            }

        }

    }
}

