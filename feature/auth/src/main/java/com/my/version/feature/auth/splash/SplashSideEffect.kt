package com.my.version.feature.auth.splash

sealed class SplashSideEffect{
    data object NavigateToSignIn : SplashSideEffect()
    data object NavigateToHome : SplashSideEffect()

}