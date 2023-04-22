package com.xdiach.auth.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import com.xdiach.auth.AuthenticationScreen
import com.xdiach.translations.R
import com.xdiach.auth.presentation.viewmodel.AuthenticationViewModel
import com.xdiach.ui.UiText
import com.xdiach.util.Screen

fun NavGraphBuilder.authenticationRoute(
    navigateToHome: () -> Unit,
    onDataLoaded: () -> Unit
) {
    composable(route = Screen.Authentication.route) {
        val viewModel: AuthenticationViewModel = viewModel()
        val authenticated by viewModel.authenticated
        val loadingState by viewModel.loadingState
        val oneTapAuthState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()
        val context = LocalContext.current

        LaunchedEffect(key1 = kotlin.Unit) {
            onDataLoaded()
        }

        AuthenticationScreen(
            authenticated = authenticated,
            loadingState = loadingState,
            oneTapAuthState = oneTapAuthState,
            messageBarState = messageBarState,
            onAuthClicked = {
                oneTapAuthState.open()
                viewModel.setLoading(true)
            },
            onSuccessfulFirebaseSignIn = { tokenId ->
                viewModel.signInWithMongoAtlas(
                    tokenId = tokenId,
                    onSuccess = {
                        messageBarState.addSuccess(
                            UiText.StringResource(
                                resId = R.string.auth_screen_signin_successful
                            ).asString(context)
                        )
                        viewModel.setLoading(false)
                    },
                    onError = { exception ->
                        messageBarState.addError(
                            exception ?: Exception(
                                UiText.StringResource(R.string.auth_screen_signin_failure)
                                    .asString(context)
                            )
                        )
                        viewModel.setLoading(false)
                    }
                )
            },
            onFailedFirebaseSignIn = { exception ->
                messageBarState.addError(exception)
                viewModel.setLoading(false)
            },
            onDialogDismissed = { message ->
                messageBarState.addError(Exception(message))
                viewModel.setLoading(false)
            },
            navigateToHome = navigateToHome
        )
    }
}
