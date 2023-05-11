package com.xdiach.home.presentation.tab.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.xdiach.common.domain.model.ThemeMode
import com.xdiach.home.presentation.viewmodel.SettingsViewModel
import org.koin.androidx.compose.viewModel

@Composable
fun SettingsScreenLayout(
    paddingValues: PaddingValues
) {
    val settingsViewModel: SettingsViewModel by viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .navigationBarsPadding()
            .padding(top = paddingValues.calculateTopPadding()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { settingsViewModel.saveThemeMode(ThemeMode.DARK.id) }) {
            Text("Dark")
        }
        Button(onClick = { settingsViewModel.saveThemeMode(ThemeMode.LIGHT.id) }) {
            Text("Light")
        }
        Button(onClick = { settingsViewModel.saveThemeMode(ThemeMode.SYSTEM.id) }) {
            Text("System")
        }
    }
}
