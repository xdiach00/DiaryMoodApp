package com.xdiach.home.presentation.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.xdiach.home.model.Entry
import com.xdiach.util.model.Mood

@RequiresApi(Build.VERSION_CODES.O)
internal val bottomAxisValueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, chartValues ->
    (chartValues.chartEntryModel.entries.first().getOrNull(value.toInt()) as? Entry)
        ?.localDate
        ?.run { "$dayOfMonth.$monthValue.$year" }
        .orEmpty()
}

internal val startAxisValueFormatter = AxisValueFormatter<AxisPosition.Vertical.Start> { value, chartValues ->
    (value.toInt())
        .run {
            Log.d("VALUE:", "$this")
            getMoodNameByPower(this)
        }
}

fun getMoodNameByPower(power: Int): String {
    for (mood in Mood.values()) if (mood.power == power) return mood.name
    return ""
}
