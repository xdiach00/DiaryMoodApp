package com.xdiach.util.model

import androidx.compose.ui.graphics.Color
import com.xdiach.ui.theme.*
import com.xdiach.util.R
import com.xdiach.translations.R as RT


enum class Mood(
    val icon: Int,
    val contentColor: Color,
    val containerColor: Color,
    val power: Int,
    val stringResourceId: Int
) {
    Neutral(
        icon = R.drawable.neutral,
        contentColor = Color.Black,
        containerColor = NeutralColor,
        power = 0,
        stringResourceId = RT.string.mood_neutral
    ),
    Happy(
        icon = R.drawable.happy,
        contentColor = Color.Black,
        containerColor = HappyColor,
        power = 2,
        stringResourceId = RT.string.mood_happy
    ),
    Angry(
        icon = R.drawable.angry,
        contentColor = Color.White,
        containerColor = AngryColor,
        power = -3,
        stringResourceId = RT.string.mood_angry
    ),
    Bored(
        icon = R.drawable.bored,
        contentColor = Color.Black,
        containerColor = BoredColor,
        power = -1,
        stringResourceId = RT.string.mood_bored
    ),
    Calm(
        icon = R.drawable.calm,
        contentColor = Color.Black,
        containerColor = CalmColor,
        power = 1,
        stringResourceId = RT.string.mood_calm
    ),
    Depressed(
        icon = R.drawable.depressed,
        contentColor = Color.Black,
        containerColor = DepressedColor,
        power = -3,
        stringResourceId = RT.string.mood_depressed
    ),
    Disappointed(
        icon = R.drawable.disappointed,
        contentColor = Color.White,
        containerColor = DisappointedColor,
        power = -2,
        stringResourceId = RT.string.mood_disappointed
    ),
    Humorous(
        icon = R.drawable.humorous,
        contentColor = Color.Black,
        containerColor = HumorousColor,
        power = 4,
        stringResourceId = RT.string.mood_humorous
    ),
    Lonely(
        icon = R.drawable.lonely,
        contentColor = Color.White,
        containerColor = LonelyColor,
        power = -2,
        stringResourceId = RT.string.mood_lonely
    ),
    Mysterious(
        icon = R.drawable.mysterious,
        contentColor = Color.Black,
        containerColor = MysteriousColor,
        power = 1,
        stringResourceId = RT.string.mood_mysterious
    ),
    Romantic(
        icon = R.drawable.romantic,
        contentColor = Color.White,
        containerColor = RomanticColor,
        power = 3,
        stringResourceId = RT.string.mood_romantic
    ),
    Shameful(
        icon = R.drawable.shameful,
        contentColor = Color.White,
        containerColor = ShamefulColor,
        power = -1,
        stringResourceId = RT.string.mood_shameful
    ),
    Awful(
        icon = R.drawable.awful,
        contentColor = Color.Black,
        containerColor = AwfulColor,
        power = -4,
        stringResourceId = RT.string.mood_awful
    ),
    Surprised(
        icon = R.drawable.surprised,
        contentColor = Color.Black,
        containerColor = SurprisedColor,
        power = 1,
        stringResourceId = RT.string.mood_surprised
    ),
    Suspicious(
        icon = R.drawable.suspicious,
        contentColor = Color.Black,
        containerColor = SuspiciousColor,
        power = 0,
        stringResourceId = RT.string.mood_suspicious
    ),
    Tense(
        icon = R.drawable.tense,
        contentColor = Color.Black,
        containerColor = TenseColor,
        power = -1,
        stringResourceId = RT.string.mood_tense
    )
}
