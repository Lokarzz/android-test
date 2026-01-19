package com.catchdesign.common.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.catchdesign.common.R
import com.catchdesign.common.color.CeriseRed


@Composable
fun BackgroundScreen(modifier: Modifier = Modifier, show: Boolean = true) {
    AnimatedVisibility(show, exit = fadeOut()) {
        Box(modifier = modifier.background(CeriseRed)) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center),
                painter = painterResource(R.drawable.catch_design_logo),
                contentDescription = stringResource(R.string.catch_design_logo)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    BackgroundScreen(modifier = Modifier.fillMaxSize())
}