@file:Suppress("MagicNumber")

package com.xdiach.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.xdiach.ui.R as RU
import com.xdiach.translations.R as RT
import com.xdiach.ui.components.GoogleButton
import com.xdiach.ui.values.Dimensions

@Composable
fun AuthenticationScreenLayout(
    loadingState: Boolean,
    onAuthClicked: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(9f)
                .fillMaxWidth()
                .padding(40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(10f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(120.dp),
                    painter = painterResource(id = RU.drawable.google_logo),
                    contentDescription = stringResource(id = RT.string.app_name)
                )
                Spacer(modifier = Modifier.height(Dimensions.L))
                Text(
                    text = stringResource(id = RT.string.auth_screen_welcome),
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )
                Text(
                    text = stringResource(id = RT.string.auth_screen_subtitle),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                )
            }
            Column(
                modifier = Modifier.weight(weight = 2f),
                verticalArrangement = Arrangement.Bottom
            ) {
                GoogleButton(
                    loadingState = loadingState,
                    onClick = onAuthClicked
                )
            }
        }
    }
}
