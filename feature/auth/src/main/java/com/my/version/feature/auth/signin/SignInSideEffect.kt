package com.my.version.feature.auth.signin

import androidx.annotation.StringRes

sealed class SignInSideEffect {
    data class ShowToast(@StringRes val message: Int) : SignInSideEffect()
    data object NavigateToHome: SignInSideEffect()
    data object NavigateToSignUp: SignInSideEffect()
}