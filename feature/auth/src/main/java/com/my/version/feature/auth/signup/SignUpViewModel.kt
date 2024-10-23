package com.my.version.feature.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.feature.auth.signup.state.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
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
            emailText = email
        )
    }

    fun onPasswordTextChange(password: String) = _uiState.update { currentState ->
        currentState.copy(
            passwordText = password
        )
    }

    fun onConfirmPasswordTextChange(confirmPassword: String) = _uiState.update { currentState ->
        currentState.copy(
            confirmPasswordText = confirmPassword
        )
    }

    fun onSignUpButtonClick() = viewModelScope.launch {
        _sideEffect.emit(SignUpSideEffect.NavigateUp)
    }
}