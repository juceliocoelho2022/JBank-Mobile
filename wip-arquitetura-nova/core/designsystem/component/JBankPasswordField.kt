package com.jucelio.jbankmobile.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun JBankPasswordField(

    value: String,

    onValueChange: (String) -> Unit

) {

    OutlinedTextField(

        value = value,

        onValueChange = onValueChange,

        label = {

            Text("Senha")

        },

        visualTransformation = PasswordVisualTransformation(),

        modifier = Modifier.fillMaxWidth(),

        singleLine = true

    )

}