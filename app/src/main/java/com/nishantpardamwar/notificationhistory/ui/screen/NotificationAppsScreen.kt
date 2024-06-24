package com.nishantpardamwar.notificationhistory.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.nishantpardamwar.notificationhistory.R
import com.nishantpardamwar.notificationhistory.models.NotificationAppModel
import com.nishantpardamwar.notificationhistory.viewmodel.HomeViewModel

@Composable
fun NotificationAppsScreen() {
    val vm = hiltViewModel<HomeViewModel>()
    val apps = vm.getNotificationApps().collectAsLazyPagingItems()

    LazyColumn(Modifier.fillMaxSize()) {
        items(
            apps.itemCount,
            key = apps.itemKey { it.appPkg }) { index: Int ->
            val item = apps[index]
            if (item != null) {
                AppItemRow(item = item) {

                }
            }
        }
    }
}

@Composable
private fun AppItemRow(item: NotificationAppModel, onItemClick: (NotificationAppModel) -> Unit) {
    Row(
        Modifier
            .clickable { onItemClick(item) }
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .height(56.dp),
        Arrangement.SpaceBetween,
        Alignment.CenterVertically) {
        Row(Modifier.wrapContentWidth()) {
            if (item.icon != null) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    bitmap = item.icon,
                    contentDescription = "App Icon"
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(
                            color = colorResource(id = R.color.color_CDD0D3),
                            shape = CircleShape
                        )
                )
            }

            Spacer(modifier = Modifier.padding(horizontal = 5.dp))

            Text(
                text = item.title,
                fontSize = 16.sp,
                color = colorResource(id = R.color.color_0078E7)
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .padding(top = 3.dp),
                text = item.lastDate,
                fontSize = 10.sp,
                color = colorResource(id = R.color.color_0078E7)
            )
        }
        Icon(
            modifier = Modifier.size(10.dp),
            painter = painterResource(id = R.drawable.ic_chevron),
            contentDescription = "",
            tint = colorResource(id = R.color.color_0078E7)
        )
    }
}