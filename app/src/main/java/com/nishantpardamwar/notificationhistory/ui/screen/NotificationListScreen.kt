package com.nishantpardamwar.notificationhistory.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.nishantpardamwar.notificationhistory.models.NotificationItemModel
import com.nishantpardamwar.notificationhistory.viewmodel.MainVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationListScreen(appName: String, appPkg: String) {
    val context = LocalContext.current
    val vm = hiltViewModel<MainVM>()
    val notifications = vm.notificationFlow.collectAsLazyPagingItems()
    var appIcon by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(Unit) {
        vm.loadNotificationFor(appPkg)
    }

    LaunchedEffect(Unit) {
        appIcon =
            context.packageManager.getApplicationIcon(appPkg).toBitmapOrNull()?.asImageBitmap()
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
            LargeTopAppBar(title = {
                Text(
                    modifier = Modifier,
                    text = appName,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                )
            }, navigationIcon = {
                val icon = appIcon
                if (icon != null) {
                    Image(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(40.dp),
                        bitmap = icon,
                        contentDescription = ""
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(40.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary, shape = CircleShape
                            )
                    )
                }
            }, scrollBehavior = scrollBehavior)
        }, content = { paddingValues ->
            if (notifications.itemCount == 0 && notifications.loadState.refresh is LoadState.NotLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 60.dp),
                        text = "No Recent Notifications",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    NotificationList(notifications = notifications, onDelete = { id ->
                        vm.deleteNotification(id)
                    })
                }
            }
        })
}

@Composable
private fun NotificationList(
    notifications: LazyPagingItems<NotificationItemModel>, onDelete: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(notifications.itemCount, key = notifications.itemKey { it.id }) { index ->
            val notification = notifications[index]
            if (notification != null) {
                NotificationItem(notification = notification, onDelete = onDelete)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun NotificationItem(
    notification: NotificationItemModel, onDelete: (Long) -> Unit
) {

    Column(
        Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 5.dp),
                text = notification.title,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.secondary
            )
            Icon(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .clickable {
                        onDelete(notification.id)
                    },
                imageVector = Icons.Default.Delete,
                tint = MaterialTheme.colorScheme.secondary,
                contentDescription = "Delete Notification"
            )
        }


        if (notification.content != null) {
            Text(
                text = notification.content,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(3.dp))
        }

        Text(
            text = notification.displayDateTime, fontSize = 12.sp
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun NotificationList_Preview() {

    val item = NotificationItemModel(
        id = 0,
        title = "Notification Title",
        content = "You Received Rs.1000",
        displayDateTime = "10-12-2024 12:20 PM",
    )
    NotificationItem(notification = item) {

    }
}