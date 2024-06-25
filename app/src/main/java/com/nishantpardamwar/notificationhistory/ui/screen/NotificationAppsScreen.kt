package com.nishantpardamwar.notificationhistory.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.nishantpardamwar.notificationhistory.R
import com.nishantpardamwar.notificationhistory.models.NotificationAppModel
import com.nishantpardamwar.notificationhistory.viewmodel.MainVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationAppsScreen(
    onItemClick: (NotificationAppModel) -> Unit, onSettingClick: () -> Unit
) {
    val vm = hiltViewModel<MainVM>()
    val apps = vm.appsFlow.collectAsLazyPagingItems()

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Notification History",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
        }, navigationIcon = {
            Image(
                modifier = Modifier.padding(start = 16.dp, end = 10.dp),
                painter = painterResource(id = R.drawable.ic_bell),
                contentDescription = "BellIcon",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
            )
        }, actions = {
            Icon(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable(onClick = onSettingClick),
                imageVector = Icons.Default.Settings,
                tint = MaterialTheme.colorScheme.secondary,
                contentDescription = "Settings"
            )
        })
    }, content = { paddingValues ->
        if (apps.itemCount == 0) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp),
                    text = "Nothing to show\nAny new notification will start appearing here.",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(apps.itemCount, key = apps.itemKey { it.appPkg }) { index: Int ->
                    val item = apps[index]
                    if (item != null) {
                        AppItemRow(item = item, onItemClick = onItemClick)
                    }
                }
            }
        }
    })
}

@Composable
private fun AppItemRow(item: NotificationAppModel, onItemClick: (NotificationAppModel) -> Unit) {
    Row(
        Modifier
            .clickable { onItemClick(item) }
            .fillMaxWidth()
            .height(56.dp),
        Arrangement.SpaceBetween,
        Alignment.CenterVertically) {
        Row(
            Modifier
                .fillMaxWidth()
                .weight(.4f)
        ) {
            if (item.icon != null) {
                Image(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape),
                    bitmap = item.icon,
                    contentDescription = "App Icon"
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary, shape = CircleShape
                        )
                )
            }

            Spacer(modifier = Modifier.padding(horizontal = 5.dp))

            Text(
                text = item.appName,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .padding(top = 3.dp),
                text = item.lastDate,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .padding(bottom = 3.5.dp)
                    .size(width = 30.dp, height = 20.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = item.displayNotificationCount,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
            Icon(
                modifier = Modifier.size(10.dp),
                painter = painterResource(id = R.drawable.ic_chevron),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}