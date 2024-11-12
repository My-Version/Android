package com.my.version.feature.auth.splash

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.my.version.core.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
) : ViewModel() {
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
            /*TODO: 가능하다면 Token 자체의 유효성을 검증하는 API를 추가할 것*/
            if (tokenRepository.getTokenFromPreference() != null) {
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