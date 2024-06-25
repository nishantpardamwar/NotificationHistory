package com.nishantpardamwar.notificationhistory.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchBox(
    modifier: Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var searchInput by remember {
        mutableStateOf("")
    }

    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(
        modifier = modifier
    ) {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.secondary, RoundedCornerShape(10.dp)
                )
                .padding(10.dp)
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused
                },
            value = searchInput,
            onValueChange = {
                searchInput = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.SemiBold
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onPrimary)
        )

        if (isHintDisplayed) {
            Text(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .align(Alignment.CenterStart),
                text = hint, color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SearchBox_Preview() {
    SearchBox(Modifier)
}