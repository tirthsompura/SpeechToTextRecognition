package com.example.speechtotextrecognition.home

import android.Manifest
import android.annotation.SuppressLint
import android.provider.CalendarContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speechtotextrecognition.MainViewModel
import com.example.speechtotextrecognition.R
import com.example.speechtotextrecognition.SpeechRecognizerContract
import com.example.speechtotextrecognition.component.WavesAnimation
import com.example.speechtotextrecognition.component.bottomButton
import com.example.speechtotextrecognition.ui.theme.blackColor
import com.example.speechtotextrecognition.ui.theme.lightPurple
import com.example.speechtotextrecognition.ui.theme.lightWhitePurple
import com.example.speechtotextrecognition.ui.theme.whiteColor
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    val gradientGreenRed = Brush.verticalGradient(0f to blackColor, 1000f to lightPurple)
    val permissionState = rememberPermissionState(
        permission = Manifest.permission.RECORD_AUDIO
    )
    SideEffect {
        permissionState.launchPermissionRequest()
    }

    val speechRecognizerLauncher = rememberLauncherForActivityResult(
        contract = SpeechRecognizerContract(),
        onResult = {
            viewModel.changeTextValue(it.toString())
        }
    )

    Scaffold(content = {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradientGreenRed)
                .padding(top = 60.dp, start = 15.dp, end = 15.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Voice Recognition",
                    modifier = Modifier,
                    color = whiteColor,
                    fontSize = 28.sp
                )
                Text(
                    text = "Save Time With Our Speech Transcription.",
                    modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                    color = whiteColor.copy(alpha = 0.6f),
                    fontSize = 18.sp
                )
            }

            Box(
                modifier = Modifier
                    .size(300.dp),
                contentAlignment = Alignment.Center
            ) {
                if ((viewModel.state.text?.isNotEmpty() == true) || (viewModel.state.text != null)) {
                    WavesAnimation()
                    Text(
                        text = viewModel.state.text.toString(),
                        modifier = Modifier,
                        color = whiteColor,
                        textAlign = TextAlign.Center,
                        maxLines = 5
                    )
                } else {
                    Text(
                        text = "Press the button and speak...",
                        modifier = Modifier.padding(bottom = 0.dp),
                        color = whiteColor
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(start = 15.dp, end = 15.dp, bottom = 12.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                bottomButton(
                    painter = painterResource(id = R.drawable.ic_mic),
                    bottomPadding = 0.dp,
                    iconSize = 40.dp,
                    boxSize = 80.dp,
                    borderSize = 4.dp,
                    offsetY = (-30).dp,
                    borderColor = lightWhitePurple.copy(alpha = 0.7f),
                    iconColor = lightWhitePurple,
                    backGroundColor = blackColor.copy(alpha = 0.6f)
                ) {
                    if (permissionState.status.isGranted) {
                        speechRecognizerLauncher.launch(Unit)
                    } else
                        permissionState.launchPermissionRequest()
                }
            }
        }
    })
}










