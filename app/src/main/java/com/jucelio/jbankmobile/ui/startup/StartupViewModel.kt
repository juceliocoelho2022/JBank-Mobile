package com.jucelio.jbankmobile.ui.startup
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jucelio.jbankmobile.core.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class StartupDestination {
    WELCOME,
    HOME
}

@HiltViewModel
class StartupViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    var destination by mutableStateOf<StartupDestination?>(null)
        private set

    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch {

            Log.d("JBANK", "Startup -> WELCOME")

            destination = StartupDestination.WELCOME
        }
    }
}