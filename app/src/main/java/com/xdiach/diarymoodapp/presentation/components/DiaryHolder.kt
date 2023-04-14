package com.xdiach.diarymoodapp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xdiach.diarymoodapp.R
import com.xdiach.diarymoodapp.model.Diary
import com.xdiach.diarymoodapp.model.Mood
import com.xdiach.diarymoodapp.ui.theme.DiaryMoodAppTheme
import com.xdiach.diarymoodapp.ui.values.Dimensions
import com.xdiach.diarymoodapp.ui.values.Elevation
import com.xdiach.diarymoodapp.util.toInstant
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

@Composable
fun DiaryHolder(diary: Diary, onClick: (String) -> Unit) {
    val localDensity = LocalDensity.current
    var componentHeight by remember { mutableStateOf(0.dp) }
    var galleryOpened by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                }
            ) { onClick(diary._id.toString()) },
    ) {
        Spacer(modifier = Modifier.width(Dimensions.Padding))
        Surface(
            modifier = Modifier
                .width(Dimensions.XS)
                .height(componentHeight + Dimensions.Padding),
            tonalElevation = Elevation.Level1,
            content = {},
        )
        Spacer(modifier = Modifier.width(20.dp))
        Surface(
            modifier = Modifier
                .clip(shape = Shapes().medium)
                .onGloballyPositioned {
                    componentHeight = with(localDensity) { it.size.height.toDp() }
                },
            tonalElevation = Elevation.Level1
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                DiaryHeader(moodName = diary.mood, time = diary.date.toInstant())
                Text(
                    modifier = Modifier.padding(Dimensions.Padding),
                    text = diary.description,
                    style = TextStyle(fontSize = MaterialTheme.typography.bodyLarge.fontSize),
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                )
                if (diary.images.isNotEmpty()) {
                    ShowGalleryButton(
                        galleryOpened = galleryOpened,
                        onClick = { galleryOpened = !galleryOpened },
                    )
                }
                AnimatedVisibility(visible = galleryOpened) {
                    Column(modifier = Modifier.padding(Dimensions.Padding)) {
                        Gallery(images = diary.images)
                    }
                }
            }
        }
    }
}

@Composable
fun DiaryHeader(
    moodName: String,
    time: Instant
) {
    val mood by remember { mutableStateOf(Mood.valueOf(moodName)) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(mood.containerColor)
            .padding(horizontal = Dimensions.Padding, vertical = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier.size(18.dp),
                painter = painterResource(id = mood.icon),
                contentDescription = "Mood Icon",
            )
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = mood.name,
                color = mood.contentColor,
                style = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
            )
        }
        Text(
            text = SimpleDateFormat("hh:mm a", Locale.US)
                .format(Date.from(time)),
            color = mood.contentColor,
            style = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize)
        )
    }
}

@Composable
fun ShowGalleryButton(
    galleryOpened: Boolean,
    onClick: () -> Unit,
) {
    TextButton(onClick = onClick) {
        Text(
            text =
            if (galleryOpened)
                stringResource(id = R.string.home_gallery_hide)
            else
                stringResource(id = R.string.home_gallery_show),
            style = TextStyle(fontSize = MaterialTheme.typography.bodySmall.fontSize),
        )
    }
}

@Composable
@Preview
private fun Preview() {
    DiaryMoodAppTheme {
        DiaryHolder(
            diary = Diary().apply {
                title = "MyDiary"
                description =
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                mood = Mood.Happy.name
            },
            onClick = {},
        )
    }
}