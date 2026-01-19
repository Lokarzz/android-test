package com.catchdesign.common.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.catchdesign.common.R
import com.catchdesign.common.color.GhostWhite
import androidx.compose.material3.CenterAlignedTopAppBar as MaterialCenterAlignedTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterAlignedTopAppBar(
    modifier: Modifier = Modifier,
    name: String,
    onBackPress: () -> Unit
) {

    MaterialCenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = GhostWhite),
        title = {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = name, fontSize = 18.sp, maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black
            )
        },
        navigationIcon = {
            Row(
                modifier = Modifier.clickable(onClick = onBackPress),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                    contentDescription = "Back Arrow",
                    tint = Color.Blue
                )
                Text(
                    text = stringResource(R.string.back),
                    color = Color.Blue,
                    fontSize = 18.sp,
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    CenterAlignedTopAppBar(
        name = "Lorem Ipsum",
        onBackPress = {}
    )
}