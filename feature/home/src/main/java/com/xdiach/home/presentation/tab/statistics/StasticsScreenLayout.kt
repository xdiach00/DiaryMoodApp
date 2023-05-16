package com.xdiach.home.presentation.tab.statistics

import android.graphics.Typeface
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.chart.decoration.ThresholdLine
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.xdiach.home.presentation.components.EmptyPage
import com.xdiach.home.presentation.utils.bottomAxisValueFormatter
import com.xdiach.home.presentation.utils.rememberChartStyle
import com.xdiach.home.presentation.utils.startAxisValueFormatter
import com.xdiach.home.presentation.viewmodel.HomeViewModel
import com.xdiach.translations.R
import com.xdiach.ui.theme.MysteriousColor
import com.xdiach.ui.theme.RomanticColor
import com.xdiach.ui.values.Dimensions
import com.xdiach.util.model.Diary
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

private const val MAX_LABEL_COUNT = 7

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatisticsScreenLayout(
    paddingValues: PaddingValues,
    diaryNotes: Map<LocalDate, List<Diary>>,
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val chartEntryModelProducer = ChartEntryModelProducer(viewModel.getMoodStatistics(diaryNotes))
    val context = LocalContext.current
    val lineChartColors = listOf(MysteriousColor)
    val columnChartColors = listOf(RomanticColor)
    val thresholdLine = rememberThresholdLine()

    if (diaryNotes.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .navigationBarsPadding()
                .padding(top = paddingValues.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.home_screen_statistic) + ":",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Light
                )
            )
            Spacer(modifier = Modifier.height(Dimensions.L))
            ProvideChartStyle(
                rememberChartStyle(
                    lineChartColors = lineChartColors,
                    columnChartColors = columnChartColors
                )
            ) {
                Chart(
                    chart = lineChart(),
                    chartModelProducer = chartEntryModelProducer,
                    startAxis = startAxis(
                        valueFormatter = startAxisValueFormatter(context = context),
                        maxLabelCount = MAX_LABEL_COUNT
                    ),
                    bottomAxis = bottomAxis(
                        valueFormatter = bottomAxisValueFormatter,
                        guideline = null
                    )
                )
                Spacer(modifier = Modifier.height(Dimensions.Spacer))
                Chart(
                    chart = columnChart(decorations = remember(thresholdLine) { listOf(thresholdLine) }),
                    chartModelProducer = chartEntryModelProducer,
                    startAxis = startAxis(
                        valueFormatter = startAxisValueFormatter(context = context),
                        maxLabelCount = MAX_LABEL_COUNT
                    ),
                    bottomAxis = bottomAxis(valueFormatter = bottomAxisValueFormatter)
                )
            }
        }
    } else {
        EmptyPage(
            title = stringResource(id = R.string.home_screen_stats_empty_title),
            subtitle = stringResource(id = R.string.home_screen_stats_empty_description)
        )
    }
}

@Composable
private fun rememberThresholdLine(): ThresholdLine {
    val thresholdLineValue = 0f
    val thresholdLineLabelPadding = dimensionsOf(Dimensions.M, Dimensions.XS)
    val thresholdLineLabelMargins = dimensionsOf(Dimensions.S)
    val line = shapeComponent(strokeWidth = Dimensions.XS, strokeColor = MaterialTheme.colorScheme.onSurface)
    val label = textComponent(
        color = MaterialTheme.colorScheme.onSurface,
        background = shapeComponent(Shapes.pillShape, MaterialTheme.colorScheme.surface),
        padding = thresholdLineLabelPadding,
        margins = thresholdLineLabelMargins,
        typeface = Typeface.MONOSPACE,
    )
    return remember(line, label) {
        ThresholdLine(thresholdValue = thresholdLineValue, lineComponent = line, labelComponent = label)
    }
}
