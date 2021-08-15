package com.nishantpardamwar.notificationhistory.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nishantpardamwar.notificationhistory.Action
import com.nishantpardamwar.notificationhistory.R
import com.nishantpardamwar.notificationhistory.database.entities.NotificationEntity
import com.nishantpardamwar.notificationhistory.getAppIcon
import com.nishantpardamwar.notificationhistory.isNightMode
import com.nishantpardamwar.notificationhistory.viewmodel.HomeViewModel

@Composable
fun HomeScreen(vm: HomeViewModel, onAction: (Action) -> Unit) {
    val notificationAccessEnabled by vm.notificationAccessEnableState.collectAsState()
    val notificationList by vm.observeNotificationList().collectAsState(listOf())
    Box(Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            NotificationAccessEnable(notificationAccessEnabled, onAction = onAction)
            NotificationList(notificationList) { entity ->
                vm.deleteNotification(entity)
            }
        }
    }
}

@Composable
fun NotificationAccessEnable(isNotificationAccessEnabled: Boolean, onAction: (Action) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        if (isNotificationAccessEnabled) {
            Text(text = "Notification Access Enabled")
            Button(onClick = {
                onAction(Action.FIRE_NOTIFICATION)
            }) {
                Text(text = "Fire Test Notification")
            }
        } else {
            Button(onClick = {
                onAction(Action.ENABLE_NOTIFICATION_ACCESS)
            }) {
                Text(text = "Enable Notification Access")
            }
        }
    }
}

@Composable
fun NotificationList(
    list: List<NotificationEntity>, onDelete: (NotificationEntity) -> Unit
) {
    LazyColumn(Modifier.animateContentSize { initialValue, targetValue -> }) {
        itemsIndexed(list) { index, item ->
            NotificationItem(item, onDelete = onDelete)
            Divider(Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun NotificationItem(
    item: NotificationEntity, onDelete: (NotificationEntity) -> Unit
) {
    val context = LocalContext.current
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    val icon = getAppIcon(context, item.appPkg)
                    if (icon != null) {
                        Image(
                            modifier = Modifier.size(25.dp), bitmap = icon, contentDescription = ""
                        )
                    }
                    Text(modifier = Modifier.padding(start = 10.dp), text = item.appName)
                }

                if (item.isGroupConversation) {
                    if (isNightMode(context)) {
                        Image(painter = painterResource(id = R.drawable.ic_group), contentDescription = "")
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.ic_group),
                            colorFilter = ColorFilter.tint(Color.Black),
                            contentDescription = ""
                        )
                    }
                }
            }

            Text(modifier = Modifier.padding(top = 10.dp), text = item.title, fontWeight = FontWeight.Bold)
            if (item.content != null) {
                Text(modifier = Modifier.padding(top = 10.dp), text = item.content)
            }

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Text(modifier = Modifier.padding(top = 10.dp), text = item.displayCreatedAtDate())
                    Text(
                        modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                        text = item.displayCreatedAtTime(),
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .clickable {
                            onDelete(item)
                        }, text = "DELETE", color = Color(0XFFBB86FC)
                )
            }
        }
    }
}