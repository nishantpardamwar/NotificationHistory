package com.nishantpardamwar.notificationhistory.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nishantpardamwar.notificationhistory.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onFilterAppSettingClick: () -> Unit) {
    Scaffold(Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            Text(
                text = "Settings",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
        }, navigationIcon = {
            Icon(
                modifier = Modifier.padding(horizontal = 10.dp),
                imageVector = Icons.Default.Settings,
                tint = MaterialTheme.colorScheme.secondary,
                contentDescription = "Settings Icon"
            )
        })
    }, content = { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SettingItem(title = "Filter Apps", onClick = onFilterAppSettingClick)
        }
    })

}

@Composable
private fun SettingItem(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .fillMaxWidth()
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.secondary
        )
        Icon(
            modifier = Modifier.size(15.dp),
            painter = painterResource(id = R.drawable.ic_chevron),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun SettingItem_Preview() {
    SettingItem("Filter Apps", {})
}