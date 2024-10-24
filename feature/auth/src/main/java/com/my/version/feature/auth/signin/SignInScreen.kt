package com.my.version.feature.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.my.version.core.common.extension.noRippleClickable
import com.my.version.core.designsystem.component.button.RectangleButton
import com.my.version.core.designsystem.component.textfield.AuthTextField
import com.my.version.core.designsystem.theme.Grey350
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.theme.MyVersionTypography
import com.my.version.feature.auth.signin.state.SignInUiState
import feature.auth.R

@Composable
fun SignInRoute(
    modifier: Modifier = Modifier,
    navigateToSignUp: () -> Unit = {},
    navigateToHome: () -> Unit = {},
    viewModel: SignInViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect {sideEffect ->
                when (sideEffect) {
                    is SignInSideEffect.ShowToast -> {}
                    is SignInSideEffect.NavigateToHome -> navigateToHome()
                    is SignInSideEffect.NavigateToSignUp -> navigateToSignUp()
                }
            }

    }


    SignInScreen(
        uiState = uiState,
        onEmailValueChange = viewModel::onEmailTextChange,
        onPasswordValueChange = viewModel::onPasswordTextChange,
        onSignInButtonClick = viewModel::onSignInButtonClick,
        onSignUpButtonClick = viewModel::onSignUpButtonClick,
        modifier = modifier
    )
}

@Composable
private fun SignInScreen(
    uiState: SignInUiState,
    onEmailValueChange: (String) -> Unit,
    onPasswordValueChange: (String) -> Unit,
    onSignInButtonClick: () -> Unit,
    onSignUpButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MyVersionBackground)
    ) {
        Spacer(modifier = Modifier.weight(0.7f))

        Image(
            painter = painterResource(com.my.version.core.designsystem.R.drawable.logo_top_bar),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        )

        Spacer(modifier = Modifier.weight(0.3f))

        AuthTextField(
            value = uiState.emailText,
            hint = stringResource(R.string.signin_hint_email),
            onValueChange = onEmailValueChange,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.weight(0.05f))
        AuthTextField(
            value = uiState.passwordText,
            hint = stringResource(R.string.signin_hint_password),
            onValueChange = onPasswordValueChange,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.weight(0.1f))

        RectangleButton(
            isEnabled = true,
            text = stringResource(R.string.signin_button_login),
            textStyle = MyVersionTypography.bodyMedium,
            innerPadding = 10,
            cornerRadius = 10.dp,
            onClick = onSignInButtonClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
        )
        Spacer(modifier = Modifier.weight(0.1f))
        Text(
            text = stringResource(R.string.signin_text_no_account),
            color = Grey350,
            style = MyVersionTypography.bodySmall,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .noRippleClickable(onSignUpButtonClick)
        )



        Spacer(modifier = Modifier.weight(1f))

    }
}

@Preview(showBackground = true)
@Composable
private fun SignInRoutePreview() {
    MyVersionTheme {
        SignInScreen(
            uiState = SignInUiState(),
            onEmailValueChange = {},
            onPasswordValueChange = {},
            onSignInButtonClick = {},
            onSignUpButtonClick = {}
        )
    }
}