package com.nishantpardamwar.notificationhistory.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.nishantpardamwar.notificationhistory.models.NotificationItemModel
import com.nishantpardamwar.notificationhistory.viewmodel.MainVM

@Composable
fun NotificationListScreen(appName: String, appPkg: String) {
    val vm = hiltViewModel<MainVM>()
    val notifications = vm.notificationFlow.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        vm.loadNotificationFor(appPkg)
    }

    Column(Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(notifications.itemCount, key = notifications.itemKey { it.id }) { index ->
                val notification = notifications[index]
                if (notification != null) {
                    NotificationItem(notification = notification, onDelete = { notification ->
                        vm.deleteNotification(notification.id)
                    })
                }
            }
        }
    }
}

@Composable
private fun NotificationItem(
    notification: NotificationItemModel, onDelete: (NotificationItemModel) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = notification.title,
                    fontWeight = FontWeight.Bold
                )
            }


            if (notification.content != null) {
                Text(modifier = Modifier.padding(top = 10.dp), text = notification.content)
            }

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = notification.displayCreatedAtTime,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                        text = notification.displayCreatedAtDate
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .clickable {
                            onDelete(notification)
                        }, text = "DELETE", color = Color(0XFFBB86FC)
                )
            }
        }
    }
}