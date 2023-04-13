package com.xdiach.diarymoodapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import com.xdiach.diarymoodapp.R
import com.xdiach.diarymoodapp.presentation.screens.authentication.AuthenticationScreen
import com.xdiach.diarymoodapp.presentation.screens.authentication.AuthenticationViewModel
import com.xdiach.diarymoodapp.presentation.screens.home.HomeScreen
import com.xdiach.diarymoodapp.ui.UiText
import com.xdiach.diarymoodapp.util.Constants.WRITE_SCREEN_ARGUMENT_KEY

@Composable
fun SetupNavGraph(startDestination: String, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        authenticationRoute(
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            }
        )
        homeRoute(navigateToWrite = {
            navController.navigate(Screen.Write.route)
        })
        writeRoute()
    }
}

fun NavGraphBuilder.authenticationRoute(
    navigateToHome: () -> Unit,
) {
    composable(route = Screen.Authentication.route) {
        val viewModel: AuthenticationViewModel = viewModel()
        val authenticated by viewModel.authenticated
        val loadingState by viewModel.loadingState
        val oneTapAuthState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()
        val context = LocalContext.current

        AuthenticationScreen(
            authenticated = authenticated,
            loadingState = loadingState,
            oneTapAuthState = oneTapAuthState,
            messageBarState = messageBarState,
            onAuthClicked = {
                oneTapAuthState.open()
                viewModel.setLoading(true)
            },
            onTokenIdReceived = { tokenId ->
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
            onDialogDismissed = { message ->
                messageBarState.addError(Exception(message))
                viewModel.setLoading(false)
            },
            navigateToHome = navigateToHome,
        )
    }
}

fun NavGraphBuilder.homeRoute(
    navigateToWrite: () -> Unit,
) {
    composable(route = Screen.Home.route) {
        HomeScreen(
            onMenuClicked = {},
            navigateToWrite = navigateToWrite,
        )
    }
}

fun NavGraphBuilder.writeRoute() {
    composable(
        route = Screen.Write.route,
        arguments = listOf(navArgument(name = WRITE_SCREEN_ARGUMENT_KEY) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {

    }
}