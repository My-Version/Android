package com.my.version.feature.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.feature.auth.signin.state.SignInUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(

) : ViewModel() {
    private var _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    private var _sideEffect = MutableSharedFlow<SignInSideEffect>()
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

    fun onSignInButtonClick() = viewModelScope.launch {
        _sideEffect.emit(SignInSideEffect.NavigateToHome)
    }

    fun onSignUpButtonClick() = viewModelScope.launch {
        _sideEffect.emit(SignInSideEffect.NavigateToSignUp)

    }
}