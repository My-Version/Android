package com.my.version.feature.auth.signup

import androidx.annotation.StringRes

sealed class SignUpSideEffect {
    data class ShowToast(@StringRes val message: Int) : SignUpSideEffect()
    data object NavigateUp : SignUpSideEffect()

}