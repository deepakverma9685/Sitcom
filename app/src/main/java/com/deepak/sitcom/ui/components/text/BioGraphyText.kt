package com.deepak.sitcom.ui.components.text

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.deepak.sitcom.ui.theme.bioGrapyText


@Composable
fun BioGraphyText(text:String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bioGrapyText
    )
}
