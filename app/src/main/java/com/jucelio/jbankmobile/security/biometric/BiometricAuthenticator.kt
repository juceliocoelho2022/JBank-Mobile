package com.jucelio.jbankmobile.security.biometric

import androidx.biometric.BiometricManager
import androidx.fragment.app.FragmentActivity

class BiometricAuthenticator(
    private val activity: FragmentActivity
) {

    fun isAvailable(): Boolean {

        val biometricManager =
            BiometricManager.from(activity)

        return biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
        ) == BiometricManager.BIOMETRIC_SUCCESS
    }

}