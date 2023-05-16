package com.xdiach.home.presentation.utils

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.xdiach.home.model.Entry
import com.xdiach.ui.UiText
import com.xdiach.util.model.Mood
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
internal val bottomAxisValueFormatter =
    AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, chartValues ->
        (chartValues.chartEntryModel.entries.first().getOrNull(value.toInt()) as? Entry)
            ?.localDate
            ?.run { "$dayOfMonth.$monthValue.$year" }
            .orEmpty()
    }

internal fun startAxisValueFormatter(context: Context) =
    AxisValueFormatter<AxisPosition.Vertical.Start> { value, _ ->
        getMoodNameByPower(
            power = value.roundToInt(),
            context = context
        )
    }

fun getMoodNameByPower(power: Int, context: Context): String {
    val mood = Mood.values().find { it.power == power }
    return mood?.let { UiText.StringResource(it.stringResourceId).asString(context) } ?: ""
}
