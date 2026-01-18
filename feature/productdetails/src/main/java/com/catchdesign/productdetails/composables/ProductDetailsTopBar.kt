package com.catchdesign.productdetails.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
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
import com.catchdesign.productdetails.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProductDetailsTopBar(
    modifier: Modifier = Modifier,
    name: String,
    onBackPress: () -> Unit
) {

    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF7F8FF)),
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
    ProductDetailsTopBar(
        name = "Lorem Ipsum",
        onBackPress = {}
    )
}