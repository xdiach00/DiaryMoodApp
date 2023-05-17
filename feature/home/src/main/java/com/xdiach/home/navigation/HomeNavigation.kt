@file:Suppress("LongMethod")

package com.xdiach.home.navigation

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.xdiach.common.domain.model.ThemeMode
import com.xdiach.home.model.HomeTabs
import com.xdiach.home.presentation.HomeScreen
import com.xdiach.home.presentation.viewmodel.HomeViewModel
import com.xdiach.home.presentation.viewmodel.SettingsViewModel
import com.xdiach.translations.R
import com.xdiach.ui.UiText
import com.xdiach.ui.components.DisplayAlertDialog
import com.xdiach.util.PrivateConstants.APP_ID
import com.xdiach.util.Screen
import com.xdiach.util.model.RequestState
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.homeRoute(
    navigateToWrite: () -> Unit,
    navigateToWriteWithArgs: (String) -> Unit,
    navigateToAuth: () -> Unit,
    onDataLoaded: () -> Unit
) {
    composable(route = Screen.Home.route) {
        val homeViewModel = koinViewModel<HomeViewModel>()
        val settingsViewModel = koinViewModel<SettingsViewModel>()
        val diaries by homeViewModel.diaries
        var selectedHomeTab by remember { mutableStateOf(HomeTabs.Home) }
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
            dateIsSelected = homeViewModel.dateIsSelected,
            selectedHomeTab = selectedHomeTab,
            themeModeActive = settingsViewModel.getActiveThemeMode(),
            onDarkModeClicked = { settingsViewModel.saveThemeMode(ThemeMode.DARK) },
            onLightModeClicked = { settingsViewModel.saveThemeMode(ThemeMode.LIGHT) },
            onSystemModeClicked = { settingsViewModel.saveThemeMode(ThemeMode.SYSTEM) },
            onDateSelected = { homeViewModel.getDiaries(zonedDateTime = it) },
            onDateReset = { homeViewModel.getDiaries() },
            onMenuClicked = {
                scope.launch {
                    drawerState.open()
                }
            },
            onHomeClicked = {
                selectedHomeTab = HomeTabs.Home
                scope.launch { drawerState.close() }
            },
            onStatisticsClicked = {
                selectedHomeTab = HomeTabs.Statistics
                scope.launch { drawerState.close() }
            },
            onSettingsClicked = {
                selectedHomeTab = HomeTabs.Settings
                scope.launch { drawerState.close() }
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
                homeViewModel.deleteAllDiaries(
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
