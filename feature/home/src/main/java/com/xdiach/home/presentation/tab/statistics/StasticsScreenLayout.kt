package com.xdiach.home.presentation.tab.statistics

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
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
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollState
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.chart.scale.AutoScaleUp
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.xdiach.home.presentation.components.EmptyPage
import com.xdiach.home.presentation.utils.bottomAxisValueFormatter
import com.xdiach.home.presentation.utils.startAxisValueFormatter
import com.xdiach.home.presentation.viewmodel.HomeViewModel
import com.xdiach.translations.R
import com.xdiach.ui.values.Dimensions
import com.xdiach.util.model.Diary
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatisticsScreenLayout(
    paddingValues: PaddingValues,
    diaryNotes: Map<LocalDate, List<Diary>>,
    dateIsSelected: Boolean
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val chartEntryModelProducer = ChartEntryModelProducer(viewModel.getMoodStatistics(diaryNotes))
    val chartScrollState = rememberChartScrollState()
    val context = LocalContext.current

    if (diaryNotes.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .navigationBarsPadding()
                .padding(top = paddingValues.calculateTopPadding()),
            verticalArrangement = Arrangement.Center,
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
            if (!dateIsSelected) {
                Chart(
                    chart = lineChart(
                        pointPosition = LineChart.PointPosition.Start
                    ),
                    chartModelProducer = chartEntryModelProducer,
                    startAxis = startAxis(
                        valueFormatter = startAxisValueFormatter(context = context),
                        maxLabelCount = 7
                    ),
                    bottomAxis = bottomAxis(valueFormatter = bottomAxisValueFormatter),
                    chartScrollState = chartScrollState,
                    autoScaleUp = AutoScaleUp.None,
                    isZoomEnabled = true
                )
            } else {
                Chart(
                    chart = columnChart(),
                    chartModelProducer = chartEntryModelProducer,
                    startAxis = startAxis(valueFormatter = startAxisValueFormatter(context)),
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
