package com.catchdesign.products.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchdesign.domain.model.products.ProductUI

@Composable
internal fun ProductItem(modifier: Modifier = Modifier, productUI: ProductUI) {

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f), text = productUI.name,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = productUI.tagLine, color = Color.Gray,
                    textAlign = TextAlign.End
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = "Arrow Right"
                )
            }
        }
        HorizontalDivider()
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ProductItem(
        modifier = Modifier.fillMaxWidth(),
        productUI = ProductUI(
            id = 1,
            name = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
            tagLine = "Lorem ipsum dolor sit amet, consectetur adipiscing elit"
        )
    )
}