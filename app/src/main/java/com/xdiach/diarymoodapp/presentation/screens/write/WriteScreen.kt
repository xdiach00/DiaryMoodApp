package com.xdiach.diarymoodapp.presentation.screens.write

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.xdiach.diarymoodapp.model.Diary
import com.xdiach.diarymoodapp.model.Mood

@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WriteScreen(
    uiState: UiState,
    moodName: () -> String,
    pagerState: PagerState,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onBackPressed: () -> Unit,
    onDeleteConfirmed: () -> Unit,
    onSaveClicked: (Diary) -> Unit,
) {
    LaunchedEffect(key1 = uiState.mood) {
        pagerState.scrollToPage(Mood.valueOf(uiState.mood.name).ordinal)
    }

    Scaffold(
        topBar = {
            WriteTopBar(
                selectedDiary = uiState.selectedDiary,
                moodName = moodName,
                onBackPressed = onBackPressed,
                onDeleteConfirmed = onDeleteConfirmed,
            )
        },
        content = { paddingValues ->
            WriteScreenLayout(
                uiState = uiState,
                pagerState = pagerState,
                title = uiState.title,
                onTitleChanged = onTitleChanged,
                description = uiState.description,
                onDescriptionChanged = onDescriptionChanged,
                paddingValues = paddingValues,
                onSaveClicked = onSaveClicked,
            )
        },
    )
}