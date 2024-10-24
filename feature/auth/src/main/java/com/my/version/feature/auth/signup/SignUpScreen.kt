package com.my.version.feature.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.my.version.core.common.extension.showToast
import com.my.version.core.designsystem.component.button.RectangleButton
import com.my.version.core.designsystem.component.topappbar.NavigateUpTopAppBar
import com.my.version.core.designsystem.theme.Grey500
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.theme.MyVersionTypography
import com.my.version.feature.auth.signup.component.TitledAuthTextField
import com.my.version.feature.auth.signup.state.SignUpUiState
import feature.auth.R

@Composable
fun SignUpRoute(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is SignUpSideEffect.ShowToast -> context.showToast(context.getString(sideEffect.message))
                    is SignUpSideEffect.NavigateUp -> navigateUp()
                }
            }

    }

    SignUpScreen(
        uiState = uiState,
        navigateUp = navigateUp,
        onEmailValueChange = viewModel::onEmailTextChange,
        onPasswordValueChange = viewModel::onPasswordTextChange,
        onConfirmPasswordValueChange = viewModel::onConfirmPasswordTextChange,
        onSignUpButtonClick = viewModel::onSignUpButtonClick,
        modifier = modifier
    )

}

@Composable
private fun SignUpScreen(
    uiState: SignUpUiState,
    navigateUp: () -> Unit,
    onEmailValueChange: (String) -> Unit,
    onPasswordValueChange: (String) -> Unit,
    onConfirmPasswordValueChange: (String) -> Unit,
    onSignUpButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MyVersionBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavigateUpTopAppBar(
            title = stringResource(R.string.signup_topbar_signup),
            onNavigateUp = navigateUp
        )

        Spacer(modifier = Modifier.weight(0.5f))

        TitledAuthTextField(
            title = stringResource(R.string.signup_text_email),
            value = uiState.emailText,
            hint = stringResource(R.string.signup_hint_email),
            onValueChange = onEmailValueChange,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.weight(0.2f))
        TitledAuthTextField(
            title = stringResource(R.string.signup_text_password),
            value = uiState.passwordText,
            hint = stringResource(R.string.signup_hint_password),
            onValueChange = onPasswordValueChange,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = stringResource(R.string.signup_guid_password),
            style = MyVersionTypography.labelSmall,
            color = Grey500,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        )
        Spacer(modifier = Modifier.weight(0.2f))
        TitledAuthTextField(
            title = stringResource(R.string.signup_text_password_confirm),
            value = uiState.confirmPasswordText,
            hint = stringResource(R.string.signin_hint_password),
            onValueChange = onConfirmPasswordValueChange,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.weight(1f))

        RectangleButton(
            isEnabled = uiState.isSignUpPossible,
            text = stringResource(R.string.signup_button_signup),
            textStyle = MyVersionTypography.titleMedium,
            innerPadding = 20,
            onClick = onSignUpButtonClick,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    MyVersionTheme {
        SignUpScreen(
            uiState = SignUpUiState(),
            navigateUp = {},
            onPasswordValueChange = {},
            onEmailValueChange = {},
            onConfirmPasswordValueChange = {},
            onSignUpButtonClick = {}
        )
    }
}