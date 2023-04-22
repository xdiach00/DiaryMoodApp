@file:Suppress("LongMethod")

package com.xdiach.diarymoodapp.navigation

import android.widget.Toast
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.xdiach.auth.navigation.authenticationRoute
import com.xdiach.translations.R
import com.xdiach.util.model.Mood
import com.xdiach.util.model.RequestState
import com.xdiach.ui.components.DisplayAlertDialog
import com.xdiach.diarymoodapp.presentation.screens.home.HomeScreen
import com.xdiach.diarymoodapp.presentation.screens.home.HomeViewModel
import com.xdiach.diarymoodapp.presentation.screens.write.WriteScreen
import com.xdiach.diarymoodapp.presentation.screens.write.WriteViewModel
import com.xdiach.ui.UiText
import com.xdiach.util.Constants.WRITE_SCREEN_ARGUMENT_KEY
import com.xdiach.util.PrivateConstants.APP_ID
import com.xdiach.util.Screen
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SetupNavGraph(
    startDestination: String,
    navController: NavHostController,
    onDataLoaded: () -> Unit
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
            onDataLoaded = onDataLoaded
        )
        homeRoute(
            navigateToWrite = {
                navController.navigate(Screen.Write.route)
            },
            navigateToWriteWithArgs = { id ->
                navController.navigate(Screen.Write.passDiaryId(diaryId = id))
            },
            navigateToAuth = {
                navController.popBackStack()
                navController.navigate(Screen.Authentication.route)
            },
            onDataLoaded = onDataLoaded
        )
        writeRoute(
            onBackPressed = {
                navController.popBackStack()
            }
        )
    }
}

fun NavGraphBuilder.homeRoute(
    navigateToWrite: () -> Unit,
    navigateToWriteWithArgs: (String) -> Unit,
    navigateToAuth: () -> Unit,
    onDataLoaded: () -> Unit
) {
    composable(route = Screen.Home.route) {
        val viewModel: HomeViewModel = hiltViewModel()
        val diaries by viewModel.diaries
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        var signOutDialogOpened by remember { mutableStateOf(false) }
        var deleteAllDialogOpened by remember { mutableStateOf(false) }

        LaunchedEffect(key1 = diaries) {
            if (diaries !is RequestState.Loading) {
                onDataLoaded()
            }
        }

        HomeScreen(
            diaries = diaries,
            drawerState = drawerState,
            dateIsSelected = viewModel.dateIsSelected,
            onDateSelected = { viewModel.getDiaries(zonedDateTime = it) },
            onDateReset = { viewModel.getDiaries() },
            onMenuClicked = {
                scope.launch {
                    drawerState.open()
                }
            },
            onSignOutClicked = {
                signOutDialogOpened = true
            },
            onDeleteAllClicked = {
                deleteAllDialogOpened = true
            },
            navigateToWrite = navigateToWrite,
            navigateToWriteWithArgs = navigateToWriteWithArgs
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
            }
        )
        DisplayAlertDialog(
            title = stringResource(id = R.string.home_screen_delete_all),
            message = stringResource(id = R.string.home_screen_delete_all_description),
            dialogOpened = deleteAllDialogOpened,
            onCloseDialog = { deleteAllDialogOpened = false },
            onYesClicked = {
                viewModel.deleteAllDiaries(
                    onSuccess = {
                        Toast.makeText(
                            context,
                            UiText.StringResource(R.string.home_screen_all_deleted_successfully)
                                .asString(context),
                            Toast.LENGTH_SHORT
                        ).show()
                        scope.launch { drawerState.close() }
                        deleteAllDialogOpened = false
                    },
                    onError = {
                        Toast.makeText(
                            context,
                            if (it.message == "No Internet connection") {
                                UiText.StringResource(R.string.all_screens_no_internet)
                                    .asString(context)
                            } else {
                                it.message
                            },
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
fun NavGraphBuilder.writeRoute(
    onBackPressed: () -> Unit
) {
    composable(
        route = Screen.Write.route,
        arguments = listOf(
            navArgument(name = WRITE_SCREEN_ARGUMENT_KEY) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    ) {
        val viewModel: WriteViewModel = hiltViewModel()
        val context = LocalContext.current
        val uiState = viewModel.uiState
        val pagerState = rememberPagerState()
        val galleryState = viewModel.galleryState
        val pageNumber by remember {
            derivedStateOf { pagerState.currentPage }
        }

        WriteScreen(
            uiState = uiState,
            moodName = { Mood.values()[pageNumber].name },
            pagerState = pagerState,
            galleryState = galleryState,
            onTitleChanged = {
                viewModel.setTitle(title = it)
            },
            onDescriptionChanged = {
                viewModel.setDescription(description = it)
            },
            onBackPressed = onBackPressed,
            onDeleteConfirmed = {
                viewModel.deleteDiary(
                    onSuccess = {
                        Toast.makeText(
                            context,
                            UiText.StringResource(R.string.write_diary_delete_success).asString(
                                context
                            ),
                            Toast.LENGTH_SHORT
                        ).show()
                        onBackPressed()
                    },
                    onError = { message ->
                        Toast.makeText(
                            context,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            },
            onDateTimeUpdated = { viewModel.updateDateTime(zonedDateTime = it) },
            onSaveClicked = {
                viewModel.upsertDiary(
                    diary = it.apply { mood = Mood.values()[pageNumber].name },
                    onSuccess = { onBackPressed() },
                    onError = { message ->
                        Toast.makeText(
                            context,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            },
            onImageSelect = {
                val type = context.contentResolver.getType(it)?.split("/")?.last() ?: "jpg"
                viewModel.addImage(
                    image = it,
                    imageType = type
                )
            },
            onImageDeleteClicked = { galleryState.removeImage(it) }
        )
    }
}
