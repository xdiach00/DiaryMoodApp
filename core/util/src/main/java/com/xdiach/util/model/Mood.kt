package com.xdiach.util.model

import androidx.compose.ui.graphics.Color
import com.xdiach.ui.theme.*
import com.xdiach.util.R
import com.xdiach.translations.R as RT


enum class Mood(
    val icon: Int,
    val contentColor: Color,
    val containerColor: Color,
    val stringResourceId: Int
) {
    Neutral(
        icon = R.drawable.neutral,
        contentColor = Color.Black,
        containerColor = NeutralColor,
        stringResourceId = RT.string.mood_neutral
    ),
    Happy(
        icon = R.drawable.happy,
        contentColor = Color.Black,
        containerColor = HappyColor,
        stringResourceId = RT.string.mood_happy
    ),
    Angry(
        icon = R.drawable.angry,
        contentColor = Color.White,
        containerColor = AngryColor,
        stringResourceId = RT.string.mood_angry
    ),
    Bored(
        icon = R.drawable.bored,
        contentColor = Color.Black,
        containerColor = BoredColor,
        stringResourceId = RT.string.mood_bored
    ),
    Calm(
        icon = R.drawable.calm,
        contentColor = Color.Black,
        containerColor = CalmColor,
        stringResourceId = RT.string.mood_calm
    ),
    Depressed(
        icon = R.drawable.depressed,
        contentColor = Color.Black,
        containerColor = DepressedColor,
        stringResourceId = RT.string.mood_depressed
    ),
    Disappointed(
        icon = R.drawable.disappointed,
        contentColor = Color.White,
        containerColor = DisappointedColor,
        stringResourceId = RT.string.mood_disappointed
    ),
    Humorous(
        icon = R.drawable.humorous,
        contentColor = Color.Black,
        containerColor = HumorousColor,
        stringResourceId = RT.string.mood_humorous
    ),
    Lonely(
        icon = R.drawable.lonely,
        contentColor = Color.White,
        containerColor = LonelyColor,
        stringResourceId = RT.string.mood_lonely
    ),
    Mysterious(
        icon = R.drawable.mysterious,
        contentColor = Color.Black,
        containerColor = MysteriousColor,
        stringResourceId = RT.string.mood_mysterious
    ),
    Romantic(
        icon = R.drawable.romantic,
        contentColor = Color.White,
        containerColor = RomanticColor,
        stringResourceId = RT.string.mood_romantic
    ),
    Shameful(
        icon = R.drawable.shameful,
        contentColor = Color.White,
        containerColor = ShamefulColor,
        stringResourceId = RT.string.mood_shameful
    ),
    Awful(
        icon = R.drawable.awful,
        contentColor = Color.Black,
        containerColor = AwfulColor,
        stringResourceId = RT.string.mood_awful
    ),
    Surprised(
        icon = R.drawable.surprised,
        contentColor = Color.Black,
        containerColor = SurprisedColor,
        stringResourceId = RT.string.mood_surprised
    ),
    Suspicious(
        icon = R.drawable.suspicious,
        contentColor = Color.Black,
        containerColor = SuspiciousColor,
        stringResourceId = RT.string.mood_suspicious
    ),
    Tense(
        icon = R.drawable.tense,
        contentColor = Color.Black,
        containerColor = TenseColor,
        stringResourceId = RT.string.mood_tense
    )
}
