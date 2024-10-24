package com.my.version.feature.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.feature.auth.signup.state.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import feature.auth.R
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {
    private var _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    private var _sideEffect = MutableSharedFlow<SignUpSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onEmailTextChange(email: String) = _uiState.update { currentState ->
        currentState.copy(
            emailText = email,
            isSignUpPossible = checkIsSignUpPossible()
        )
    }

    fun onPasswordTextChange(password: String) = _uiState.update { currentState ->
        currentState.copy(
            passwordText = password,
            isSignUpPossible = checkIsSignUpPossible()
        )
    }

    fun onConfirmPasswordTextChange(confirmPassword: String) = _uiState.update { currentState ->
        currentState.copy(
            confirmPasswordText = confirmPassword,
            isSignUpPossible = checkIsSignUpPossible()
        )
    }

    private fun checkIsSignUpPossible(): Boolean =
        with(_uiState.value) { (emailText.isNotBlank() && passwordText.isNotBlank() && confirmPasswordText.isNotBlank()) }

    fun onSignUpButtonClick() = viewModelScope.launch {
        if (_uiState.value.isSignUpPossible) {
            val isPasswordMatch = isPasswordMatch()
            val isValidEmail = isValidEmail(_uiState.value.emailText)
            val isValidPassword = isValidPassword(_uiState.value.passwordText)

            when {
                isValidEmail && isValidPassword && isPasswordMatch -> {
                    with(_sideEffect) {
                        emit(SignUpSideEffect.ShowToast(R.string.signup_toast_success))
                        emit(SignUpSideEffect.NavigateUp)
                    }
                }

                !isValidEmail -> _sideEffect.emit(SignUpSideEffect.ShowToast(R.string.signup_toast_invalid_email))
                !isValidPassword -> _sideEffect.emit(SignUpSideEffect.ShowToast(R.string.signup_toast_invalid_password))
                !isPasswordMatch -> _sideEffect.emit(SignUpSideEffect.ShowToast(R.string.signup_toast_password_mismatch))

            }
        }
    }

    private fun isPasswordMatch(): Boolean = with(_uiState.value) {
        passwordText == confirmPasswordText
    }

    private fun isValidEmail(email: String): Boolean = email.matches(EMAIL_REGEX.toRegex())

    private fun isValidPassword(password: String): Boolean {
        var count = 0

        if (password.contains(UPPER_CASE_REGEX.toRegex())) count++
        if (password.contains(LOWER_CASE_REGEX.toRegex())) count++
        if (password.contains(DIGIT_REGEX.toRegex())) count++
        if (password.contains(SPECIAL_CHAR_REGEX.toRegex())) count++

        return password.length in PWD_LENGTH_MIN..PWD_LENGTH_MAX && count >= PWD_TYPE_MIX
    }

    companion object {
        private const val PWD_LENGTH_MIN = 8
        private const val PWD_LENGTH_MAX = 20
        private const val PWD_TYPE_MIX = 3

        const val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        const val UPPER_CASE_REGEX = "[A-Z]"
        const val LOWER_CASE_REGEX = "[a-z]"
        const val DIGIT_REGEX = "[0-9]"
        const val SPECIAL_CHAR_REGEX = "[!@#\$%^&*(),.?\":{}|<>]"

    }
}