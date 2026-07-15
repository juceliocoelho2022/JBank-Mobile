package com.jucelio.jbankmobile.ui.pix

import android.Manifest
import android.content.Context
import android.provider.ContactsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.jucelio.jbankmobile.ui.pix.CentralPixScreen
import com.jucelio.jbankmobile.ui.pix.PixScreen
enum class PixKeyType(
    val label: String
) {
    PHONE("Telefone"),
    CPF("CPF"),
    EMAIL("E-mail"),
    RANDOM("Chave aleatória")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PixScreen(
    onBack: () -> Unit,
    onContinue: (
        keyType: PixKeyType,
        pixKey: String,
        amount: String,
        description: String
    ) -> Unit
) {
    val context = LocalContext.current

    var selectedType by remember {
        mutableStateOf(PixKeyType.PHONE)
    }

    var pixKey by remember {
        mutableStateOf("")
    }

    var amount by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    val contactPermissionLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.RequestPermission()
        ) { granted ->

            if (!granted) {
                errorMessage =
                    "Permissão para acessar contatos não concedida."
            }
        }

    val contactPickerLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.PickContact()
        ) { uri ->

            uri ?: return@rememberLauncherForActivityResult

            val result = readPhoneFromContact(
                context = context,
                uri = uri
            )

            if (result != null) {
                pixKey = result
                selectedType = PixKeyType.PHONE
                errorMessage = null
            } else {
                errorMessage =
                    "Não foi possível localizar o telefone do contato."
            }
        }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Enviar PIX")
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector =
                                Icons.Default.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
        ) {
            Text(
                text = "Tipo de chave",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            PixKeyType.entries.forEach { type ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedType = type
                            pixKey = ""
                            errorMessage = null
                        }
                        .padding(vertical = 6.dp)
                ) {
                    RadioButton(
                        selected = selectedType == type,
                        onClick = {
                            selectedType = type
                            pixKey = ""
                        }
                    )

                    Text(
                        text = type.label,
                        modifier = Modifier.padding(
                            top = 12.dp
                        )
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            OutlinedTextField(
                value = pixKey,
                onValueChange = {
                    pixKey = it
                    errorMessage = null
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        when (selectedType) {
                            PixKeyType.PHONE ->
                                "Número de telefone"

                            PixKeyType.CPF ->
                                "CPF"

                            PixKeyType.EMAIL ->
                                "E-mail"

                            PixKeyType.RANDOM ->
                                "Chave aleatória"
                        }
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = when (selectedType) {
                        PixKeyType.PHONE ->
                            KeyboardType.Phone

                        PixKeyType.CPF ->
                            KeyboardType.Number

                        PixKeyType.EMAIL ->
                            KeyboardType.Email

                        PixKeyType.RANDOM ->
                            KeyboardType.Text
                    }
                ),
                trailingIcon = {
                    if (selectedType == PixKeyType.PHONE) {
                        IconButton(
                            onClick = {
                                contactPermissionLauncher.launch(
                                    Manifest.permission.READ_CONTACTS
                                )

                                contactPickerLauncher.launch(null)
                            }
                        ) {
                            Icon(
                                imageVector =
                                    Icons.Default.Contacts,
                                contentDescription =
                                    "Selecionar contato"
                            )
                        }
                    } else {
                        Icon(
                            imageVector =
                                Icons.Default.QrCodeScanner,
                            contentDescription = null
                        )
                    }
                }
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            OutlinedTextField(
                value = amount,
                onValueChange = {
                    amount = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Valor")
                },
                prefix = {
                    Text("R$ ")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Descrição opcional")
                },
                minLines = 2
            )

            errorMessage?.let {
                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Button(
                onClick = {
                    val validationError =
                        validatePixKey(
                            type = selectedType,
                            value = pixKey
                        )

                    if (validationError != null) {
                        errorMessage = validationError
                        return@Button
                    }

                    if (
                        amount.replace(",", ".")
                            .toBigDecimalOrNull() == null
                    ) {
                        errorMessage =
                            "Informe um valor válido."
                        return@Button
                    }

                    onContinue(
                        selectedType,
                        pixKey.trim(),
                        amount.trim(),
                        description.trim()
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Continuar")
            }
        }
    }
}

private fun validatePixKey(
    type: PixKeyType,
    value: String
): String? {
    val cleaned = value.trim()

    if (cleaned.isBlank()) {
        return "Informe a chave PIX."
    }

    return when (type) {
        PixKeyType.PHONE -> {
            val digits = cleaned.filter {
                it.isDigit()
            }

            if (digits.length < 10) {
                "Informe um telefone válido."
            } else {
                null
            }
        }

        PixKeyType.CPF -> {
            val digits = cleaned.filter {
                it.isDigit()
            }

            if (digits.length != 11) {
                "O CPF deve possuir 11 números."
            } else {
                null
            }
        }

        PixKeyType.EMAIL -> {
            if (!android.util.Patterns.EMAIL_ADDRESS
                    .matcher(cleaned)
                    .matches()
            ) {
                "Informe um e-mail válido."
            } else {
                null
            }
        }

        PixKeyType.RANDOM -> {
            if (cleaned.length < 20) {
                "Informe uma chave aleatória válida."
            } else {
                null
            }
        }
    }
}

private fun readPhoneFromContact(
    context: Context,
    uri: android.net.Uri
): String? {
    val projection = arrayOf(
        ContactsContract.CommonDataKinds.Phone.NUMBER
    )

    context.contentResolver.query(
        uri,
        projection,
        null,
        null,
        null
    )?.use { cursor ->
        val index = cursor.getColumnIndex(
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        if (
            index >= 0 &&
            cursor.moveToFirst()
        ) {
            return cursor.getString(index)
        }
    }

    return null
}