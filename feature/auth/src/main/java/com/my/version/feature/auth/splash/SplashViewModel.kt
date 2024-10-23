package com.my.version.feature.auth.splash

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {
    private var isLoginPossible: MutableState<Boolean> = mutableStateOf(false)

    private var _sideEffect = MutableSharedFlow<SplashSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun showSplash(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycleScope.launch {
            delay(DELAY_TIME)
            checkLoginPossible()
        }
    }

    private fun checkLoginPossible() {
        viewModelScope.launch {
            if (isLoginPossible.value) {
                _sideEffect.emit(SplashSideEffect.NavigateToHome)
            } else {
                _sideEffect.emit(SplashSideEffect.NavigateToSignIn)
            }
        }
    }

    companion object {
        const val DELAY_TIME = 2200L
    }
}