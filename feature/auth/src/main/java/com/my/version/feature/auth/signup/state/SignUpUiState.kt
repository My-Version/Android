package com.my.version.feature.auth.signup.state

data class SignUpUiState(
    val emailText: String = "",
    val passwordText: String = "",
    val confirmPasswordText: String = "",
    val isSignUpPossible: Boolean = false
)