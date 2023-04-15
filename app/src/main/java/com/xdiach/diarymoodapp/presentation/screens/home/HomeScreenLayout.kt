package com.xdiach.diarymoodapp.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xdiach.diarymoodapp.R
import com.xdiach.diarymoodapp.model.Diary
import com.xdiach.diarymoodapp.presentation.components.DiaryHolder
import com.xdiach.diarymoodapp.ui.values.Dimensions
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenLayout(
    diaryNotes: Map<LocalDate, List<Diary>>,
    onClick: (String) -> Unit,
) {
    if (diaryNotes.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            diaryNotes.forEach { (localDate, diaries) ->
                stickyHeader(key = localDate) {
                    DateHeader(localDate = localDate)
                }

                items(
                    items = diaries,
                    key = { it._id },
                ) {
                    DiaryHolder(diary = it, onClick = onClick)
                }
            }
        }
    } else {
        EmptyPage()
    }
}

@Composable
fun DateHeader(localDate: LocalDate) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = String.format("%02d", localDate.dayOfMonth),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Light,
                )
            )
            Text(
                text = localDate.dayOfWeek.toString().take(3),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.Light,
                )
            )
        }
        Spacer(modifier = Modifier.width(Dimensions.Padding))
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = localDate.month.toString().lowercase().replaceFirstChar {
                    it.uppercase()
                },
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Light,
                )
            )
            Text(
                text = "${localDate.year}",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.Light,
                )
            )
        }
    }
}

@Composable
fun EmptyPage(
    title: String = stringResource(id = R.string.home_diary_empty_title),
    subtitle: String = stringResource(id = R.string.home_diary_empty_subtitle),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Medium,
            )
        )
        Text(
            text = subtitle,
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.Normal,
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    DateHeader(localDate = LocalDate.now())
}