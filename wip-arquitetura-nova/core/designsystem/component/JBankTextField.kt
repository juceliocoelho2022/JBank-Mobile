package com.jucelio.jbankmobile.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun JBankTextField(

    value: String,

    label: String,

    onValueChange: (String) -> Unit

) {

    OutlinedTextField(

        value = value,

        onValueChange = onValueChange,

        label = { Text(label) },

        modifier = Modifier.fillMaxWidth(),

        singleLine = true

    )

}