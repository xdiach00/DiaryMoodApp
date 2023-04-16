package com.xdiach.diarymoodapp.presentation.screens.write

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.xdiach.diarymoodapp.model.Diary

@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WriteScreen(
    selectedDiary: Diary?,
    pagerState: PagerState,
    onBackPressed: () -> Unit,
    onDeleteConfirmed: () -> Unit,
) {
    Scaffold(
        topBar = {
            WriteTopBar(
                selectedDiary = selectedDiary,
                onBackPressed = onBackPressed,
                onDeleteConfirmed = onDeleteConfirmed,
            )
        },
        content = { paddingValues ->
            WriteScreenLayout(
                pagerState = pagerState,
                title = "",
                onTitleChanged = {},
                description = "",
                onDescriptionChanged = {},
                paddingValues = paddingValues
            )
        },
    )
}