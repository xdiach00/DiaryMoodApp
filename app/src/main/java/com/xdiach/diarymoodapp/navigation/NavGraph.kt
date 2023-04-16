package com.xdiach.diarymoodapp.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import com.xdiach.diarymoodapp.R
import com.xdiach.diarymoodapp.data.repository.MongoDB
import com.xdiach.diarymoodapp.model.Diary
import com.xdiach.diarymoodapp.presentation.components.DisplayAlertDialog
import com.xdiach.diarymoodapp.presentation.screens.authentication.AuthenticationScreen
import com.xdiach.diarymoodapp.presentation.screens.authentication.AuthenticationViewModel
import com.xdiach.diarymoodapp.presentation.screens.home.HomeScreen
import com.xdiach.diarymoodapp.presentation.screens.home.HomeViewModel
import com.xdiach.diarymoodapp.presentation.screens.write.WriteScreen
import com.xdiach.diarymoodapp.ui.UiText
import com.xdiach.diarymoodapp.util.Constants.APP_ID
import com.xdiach.diarymoodapp.util.Constants.WRITE_SCREEN_ARGUMENT_KEY
import com.xdiach.diarymoodapp.util.RequestState
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SetupNavGraph(
    startDestination: String,
    navController: NavHostController,
    onDataLoaded: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        authenticationRoute(
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            },
            onDataLoaded = onDataLoaded,
        )
        homeRoute(
            navigateToWrite = {
                navController.navigate(Screen.Write.route)
            },
            navigateToAuth = {
                navController.popBackStack()
                navController.navigate(Screen.Authentication.route)
            },
            onDataLoaded = onDataLoaded,
        )
        writeRoute(
            onBackPressed = {
                navController.popBackStack()
            }
        )
    }
}

fun NavGraphBuilder.authenticationRoute(
    navigateToHome: () -> Unit,
    onDataLoaded: () -> Unit,
) {
    composable(route = Screen.Authentication.route) {
        val viewModel: AuthenticationViewModel = viewModel()
        val authenticated by viewModel.authenticated
        val loadingState by viewModel.loadingState
        val oneTapAuthState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()
        val context = LocalContext.current

        LaunchedEffect(key1 = Unit) {
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

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.homeRoute(
    navigateToWrite: () -> Unit,
    navigateToAuth: () -> Unit,
    onDataLoaded: () -> Unit,
) {
    composable(route = Screen.Home.route) {
        val viewModel: HomeViewModel = viewModel()
        val diaries by viewModel.diaries
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var signOutDialogOpened by remember { mutableStateOf(false) }

        LaunchedEffect(key1 = diaries) {
            if (diaries !is RequestState.Loading) {
                onDataLoaded()
            }
        }

        HomeScreen(
            diaries = diaries,
            drawerState = drawerState,
            onMenuClicked = {
                scope.launch {
                    drawerState.open()
                }
            },
            onSignOutClicked = {
                signOutDialogOpened = true
            },
            navigateToWrite = navigateToWrite,
        )

        DisplayAlertDialog(
            title = stringResource(id = R.string.home_screen_signout),
            message = stringResource(id = R.string.home_screen_signout_description),
            dialogOpened = signOutDialogOpened,
            onCloseDialog = { signOutDialogOpened = false },
            onYesClicked = {
                scope.launch(Dispatchers.IO) {
                    val user = App.Companion.create(APP_ID).currentUser
                    if (user != null) {
                        user.logOut()
                        withContext(Dispatchers.Main) {
                            navigateToAuth()
                        }
                    }
                }
            },
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
fun NavGraphBuilder.writeRoute(
    onBackPressed: () -> Unit,
) {
    composable(
        route = Screen.Write.route,
        arguments = listOf(navArgument(name = WRITE_SCREEN_ARGUMENT_KEY) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        val pagerState = rememberPagerState()

        WriteScreen(
            selectedDiary = null,
            pagerState = pagerState,
            onBackPressed = onBackPressed,
            onDeleteConfirmed = {},
        )
    }
}