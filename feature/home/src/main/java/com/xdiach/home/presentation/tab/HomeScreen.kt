package com.xdiach.home.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.xdiach.home.navigation.HomeTabs
import com.xdiach.home.presentation.components.HomeTopBar
import com.xdiach.home.presentation.screen.statistics.StatisticsScreenLayout
import com.xdiach.mongo.repository.Diaries
import com.xdiach.util.Screen
import com.xdiach.translations.R as RT
import com.xdiach.ui.R as RU
import com.xdiach.util.model.RequestState
import java.time.ZonedDateTime

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun HomeScreen(
    diaries: Diaries,
    drawerState: DrawerState,
    dateIsSelected: Boolean,
    selectedHomeTab: HomeTabs,
    onDateSelected: (ZonedDateTime) -> Unit,
    onDateReset: () -> Unit,
    onMenuClicked: () -> Unit,
    onHomeClicked: () -> Unit,
    onStatisticsClicked: () -> Unit,
    onSignOutClicked: () -> Unit,
    onDeleteAllClicked: () -> Unit,
    navigateToWrite: () -> Unit,
    navigateToWriteWithArgs: (String) -> Unit

) {
    var padding by remember {
        mutableStateOf(PaddingValues())
    }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    NavigationDrawer(
        drawerState = drawerState,
        selectedHomeTab = selectedHomeTab,
        onHomeClicked = onHomeClicked,
        onStatisticsClicked = onStatisticsClicked,
        onSignOutClicked = onSignOutClicked,
        onDeleteAllClicked = onDeleteAllClicked
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                HomeTopBar(
                    scrollBehavior = scrollBehavior,
                    onMenuClicked = onMenuClicked,
                    dateIsSelected = dateIsSelected,
                    onDateSelected = onDateSelected,
                    onDateReset = onDateReset
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(end = padding.calculateEndPadding(LayoutDirection.Ltr)),
                    onClick = navigateToWrite
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "New Note Icon"
                    )
                }
            },
            content = {
                padding = it
                when (selectedHomeTab) {
                    HomeTabs.Home -> {
                        when (diaries) {
                            is RequestState.Success -> {
                                HomeScreenLayout(
                                    paddingValues = it,
                                    diaryNotes = diaries.data,
                                    onClick = navigateToWriteWithArgs
                                )
                            }

                            is RequestState.Error -> {
                                EmptyPage(
                                    title = stringResource(id = RT.string.home_diary_error_title),
                                    subtitle = "${diaries.error.message}"
                                )
                            }

                            is RequestState.Loading -> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }

                            else -> {}
                        }
                    }

                    HomeTabs.Statistics -> {
                        StatisticsScreenLayout()
                    }
                }
            }
        )
    }
}

@Composable
private fun NavigationDrawer(
    drawerState: DrawerState,
    selectedHomeTab: HomeTabs,
    onHomeClicked: () -> Unit,
    onStatisticsClicked: () -> Unit,
    onSignOutClicked: () -> Unit,
    onDeleteAllClicked: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier.size(250.dp),
                            painter = painterResource(id = RU.drawable.app_icon),
                            contentDescription = stringResource(id = RT.string.app_name)
                        )
                    }
                    NavigationDrawerItem(
                        label = {
                            Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = "Home Icon",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = stringResource(id = RT.string.home_screen_home),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        },
                        selected = selectedHomeTab == HomeTabs.Home,
                        onClick = onHomeClicked

                    )
                    NavigationDrawerItem(
                        label = {
                            Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "Statistic Icon",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = stringResource(id = RT.string.home_screen_statistic),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        },
                        selected = selectedHomeTab == HomeTabs.Statistics,
                        onClick = onStatisticsClicked
                    )
                    NavigationDrawerItem(
                        label = {
                            Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete All Diaries Icon",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = stringResource(id = RT.string.home_screen_delete_all),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        },
                        selected = false,
                        onClick = onDeleteAllClicked
                    )
                    NavigationDrawerItem(
                        label = {
                            Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                                Image(
                                    painter = painterResource(id = RU.drawable.google_logo),
                                    contentDescription = "Google logo"
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = stringResource(id = RT.string.home_screen_signout),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        },
                        selected = false,
                        onClick = onSignOutClicked
                    )
                }
            )
        },
        content = content
    )
}
