package com.xdiach.diarymoodapp.presentation.screens.authentication

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarState
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import com.xdiach.diarymoodapp.R
import com.xdiach.diarymoodapp.ui.UiText
import com.xdiach.diarymoodapp.util.Constants.CLIENT_ID
import java.lang.Exception

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthenticationScreen(
    loadingState: Boolean,
    oneTapAuthState: OneTapSignInState,
    messageBarState: MessageBarState,
    onAuthClicked: () -> Unit,
    onTokenIdReceived: (String) -> Unit,
    onDialogDismissed: (String) -> Unit,
) {
    Scaffold(
        content = {
            ContentWithMessageBar(messageBarState = messageBarState) {
                AuthenticationScreenLayout(
                    loadingState = loadingState,
                    onAuthClicked = onAuthClicked,
                )
            }
        }
    )

    OneTapSignInWithGoogle(
        state = oneTapAuthState,
        clientId = CLIENT_ID,
        onTokenIdReceived = { tokenId ->
            onTokenIdReceived(tokenId)
        },
        onDialogDismissed = { message ->
            onDialogDismissed(message)
        },
    )
}