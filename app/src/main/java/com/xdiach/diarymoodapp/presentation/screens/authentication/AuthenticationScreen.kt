package com.xdiach.diarymoodapp.presentation.screens.authentication

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthenticationScreen(
    loadingState: Boolean,
    onAuthClicked: () -> Unit,
) {
    Scaffold(
        content = {
            AuthenticationScreenLayout(
                loadingState = loadingState,
                onAuthClicked = onAuthClicked,
            )
        }
    )
}