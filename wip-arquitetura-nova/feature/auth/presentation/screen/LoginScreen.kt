package com.jucelio.jbankmobile.feature.auth.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jucelio.jbankmobile.core.designsystem.component.JBankButton
import com.jucelio.jbankmobile.core.designsystem.component.JBankLoading
import com.jucelio.jbankmobile.core.designsystem.component.JBankLogo
import com.jucelio.jbankmobile.core.designsystem.component.JBankPasswordField
import com.jucelio.jbankmobile.core.designsystem.component.JBankTextField
import com.jucelio.jbankmobile.feature.auth.presentation.event.LoginEvent
import com.jucelio.jbankmobile.feature.auth.presentation.state.LoginState

@Composable
fun LoginScreen(

    state: LoginState,

    onEvent: (LoginEvent) -> Unit

) {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement = Arrangement.Center

    ) {

        JBankLogo()

        Spacer(Modifier.height(32.dp))

        JBankTextField(

            value = state.email,

            label = "E-mail"

        ) {

            val it = ""
            onEvent(
                LoginEvent.EmailChanged(it)
            )

        }

        Spacer(Modifier.height(16.dp))

        JBankPasswordField(

            value = state.password

        ) {

            onEvent(
                LoginEvent.PasswordChanged(it)
            )

        }

        Spacer(Modifier.height(24.dp))

        JBankButton(

            text = "Entrar"

        ) {

            onEvent(
                LoginEvent.LoginClicked
            )

        }

        if (state.isLoading) {

            Spacer(Modifier.height(24.dp))

            JBankLoading()

        }

    }

}