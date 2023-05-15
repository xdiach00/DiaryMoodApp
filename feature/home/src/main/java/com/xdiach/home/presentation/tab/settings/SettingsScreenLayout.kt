package com.xdiach.home.presentation.tab.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.xdiach.common.domain.model.ThemeMode
import com.xdiach.ui.values.Dimensions
import com.xdiach.translations.R as RT
import com.xdiach.ui.R as RU

@Composable
fun SettingsScreenLayout(
    paddingValues: PaddingValues,
    themeModeActive: ThemeMode,
    onDarkModeClicked: () -> Unit,
    onLightModeClicked: () -> Unit,
    onSystemModeClicked: () -> Unit,
    onSignOutClicked: () -> Unit,
    onDeleteAllClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(top = paddingValues.calculateTopPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(250.dp),
            painter = painterResource(id = RU.drawable.app_icon),
            contentDescription = "App logo",
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(Dimensions.L))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            ThemeModeButton(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(topStart = Dimensions.L, bottomStart = Dimensions.L),
                onClick = onDarkModeClicked,
                text = stringResource(id = RT.string.settings_theme_mode_dark),
                isActive = themeModeActive == ThemeMode.DARK
            )
            ThemeModeButton(
                modifier = Modifier.weight(1f),
                shape = RectangleShape,
                onClick = onLightModeClicked,
                text = stringResource(id = RT.string.settings_theme_mode_light),
                isActive = themeModeActive == ThemeMode.LIGHT
            )
            ThemeModeButton(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(topEnd = Dimensions.L, bottomEnd = Dimensions.L),
                onClick = onSystemModeClicked,
                text = stringResource(id = RT.string.settings_theme_mode_system),
                isActive = themeModeActive == ThemeMode.SYSTEM
            )
        }
        Spacer(modifier = Modifier.height(Dimensions.Spacer))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onDeleteAllClicked() },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimensions.XL, vertical = Dimensions.Spacer),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Icon",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(Dimensions.M))
                    Text(
                        text = stringResource(id = RT.string.home_screen_delete_all),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Sign Out Arrow",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSignOutClicked() },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimensions.XL, vertical = Dimensions.Spacer),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Image(
                        painter = painterResource(id = com.xdiach.ui.R.drawable.google_logo),
                        contentDescription = "Google logo"
                    )
                    Spacer(modifier = Modifier.width(Dimensions.M))
                    Text(
                        text = stringResource(id = RT.string.home_screen_signout),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Sign Out Arrow",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun ThemeModeButton(
    modifier: Modifier = Modifier,
    shape: Shape,
    onClick: () -> Unit,
    text: String,
    isActive: Boolean
) {
    val buttonColors = if (isActive) {
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    } else {
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }

    Button(
        modifier = modifier,
        shape = shape,
        onClick = onClick,
        colors = buttonColors
    ) {
        Text(text)
    }
}
