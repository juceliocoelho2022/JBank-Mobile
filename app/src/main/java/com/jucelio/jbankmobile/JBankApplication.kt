package com.jucelio.jbankmobile

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Classe principal da aplicação.
 *
 * A anotação HiltAndroidApp inicializa o container
 * de dependências usado em todo o aplicativo.
 */
@HiltAndroidApp
class JBankApplication : Application()