package com.xdiach.diarymoodapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.xdiach.diarymoodapp.ui.theme.DiaryMoodAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiaryMoodAppTheme {

            }
        }
    }
}