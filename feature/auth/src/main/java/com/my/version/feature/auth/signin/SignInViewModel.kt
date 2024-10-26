package com.my.version.feature.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.domain.repository.AuthRepository
import com.my.version.feature.auth.signin.state.SignInUiState
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
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
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
        authRepository.postSignIn(_uiState.value.emailText, _uiState.value.passwordText)
            .onSuccess { isSignInSuccess ->
                if (isSignInSuccess) {
                    with(_sideEffect) {
                        emit(SignInSideEffect.NavigateToHome)
                        emit(SignInSideEffect.ShowToast(R.string.signin_toast_success))
                    }
                } else
                    _sideEffect.emit(SignInSideEffect.ShowToast(R.string.signin_toast_fail))
            }
            .onFailure {
                _sideEffect.emit(SignInSideEffect.ShowToast(R.string.signin_toast_fail))
            }
    }

    fun onSignUpButtonClick() = viewModelScope.launch {
        _sideEffect.emit(SignInSideEffect.NavigateToSignUp)
    }


}