package com.xdiach.home.presentation.tab.statistics

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.xdiach.translations.R
import com.xdiach.home.presentation.components.EmptyPage
import com.xdiach.home.presentation.utils.bottomAxisValueFormatter
import com.xdiach.home.presentation.utils.startAxisValueFormatter
import com.xdiach.home.presentation.viewmodel.HomeViewModel
import com.xdiach.util.model.Diary
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatisticsScreenLayout(
    diaryNotes: Map<LocalDate, List<Diary>>,
    dateIsSelected: Boolean
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val chartEntryModelProducer = ChartEntryModelProducer(viewModel.generateStats(diaryNotes))
    val context = LocalContext.current

    if (diaryNotes.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!dateIsSelected) {
                Chart(
                    chart = lineChart(),
                    chartModelProducer = chartEntryModelProducer,
                    startAxis = startAxis(valueFormatter = startAxisValueFormatter),
                    bottomAxis = bottomAxis(valueFormatter = bottomAxisValueFormatter)
                )
            } else {
                Chart(
                    chart = columnChart(),
                    chartModelProducer = chartEntryModelProducer,
                    startAxis = startAxis(valueFormatter = startAxisValueFormatter),
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
