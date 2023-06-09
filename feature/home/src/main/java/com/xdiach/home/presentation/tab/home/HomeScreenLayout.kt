package com.xdiach.home.presentation.tab.home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xdiach.home.presentation.components.DiaryHolder
import com.xdiach.home.presentation.components.EmptyPage
import com.xdiach.ui.values.Dimensions
import com.xdiach.util.model.Diary
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeScreenLayout(
    paddingValues: PaddingValues,
    diaryNotes: Map<LocalDate, List<Diary>>,
    onClick: (String) -> Unit
) {
    if (diaryNotes.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .navigationBarsPadding()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            diaryNotes.forEach { (localDate, diaries) ->
                stickyHeader(key = localDate) {
                    DateHeader(localDate = localDate)
                }

                items(
                    items = diaries,
                    key = { it._id.toString() }
                ) {
                    DiaryHolder(diary = it, onClick = onClick)
                }
            }
        }
    } else {
        EmptyPage()
    }
}

@SuppressLint("NewApi")
@Composable
private fun DateHeader(localDate: LocalDate) {
    val dateDayOfWeekLocalized = localDate.format(
        DateTimeFormatter.ofPattern("EEEE")
            .withLocale(Locale.getDefault())
    ).take(3).uppercase()
    val dateMonthLocalized =
        localDate.format(
            DateTimeFormatter.ofPattern("MMMM")
                .withLocale(Locale.getDefault())
        )
            .lowercase()
            .replaceFirstChar {
                it.uppercase()
            }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = Dimensions.Padding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = String.format(Locale.getDefault(), "%02d", localDate.dayOfMonth),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Light
                )
            )
            Text(
                text = dateDayOfWeekLocalized,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.Light
                )
            )
        }
        Spacer(modifier = Modifier.width(Dimensions.Padding))
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = dateMonthLocalized,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Light
                )
            )
            Text(
                text = "${localDate.year}",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.Light
                )
            )
        }
    }
}

@SuppressLint("NewApi")
@Composable
@Preview(showBackground = true)
private fun Preview() {
    DateHeader(localDate = LocalDate.now())
}
