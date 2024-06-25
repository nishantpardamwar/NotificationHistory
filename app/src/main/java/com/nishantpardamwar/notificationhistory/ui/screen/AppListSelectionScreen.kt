package com.nishantpardamwar.notificationhistory.ui.screen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nishantpardamwar.notificationhistory.R
import com.nishantpardamwar.notificationhistory.icons.FilterIcon
import com.nishantpardamwar.notificationhistory.models.AppItem
import com.nishantpardamwar.notificationhistory.toImageBitmap
import com.nishantpardamwar.notificationhistory.ui.SearchBox
import com.nishantpardamwar.notificationhistory.viewmodel.AppSelectionSettingsVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppListSelectionScreen() {
    val vm = hiltViewModel<AppSelectionSettingsVM>()
    val isLoading by vm.isLoadingFlow.collectAsStateWithLifecycle()
    val appList by vm.appsFlow.collectAsStateWithLifecycle()
    val searchList by vm.searchFlow.collectAsStateWithLifecycle()

    var searchQuery by remember { mutableStateOf("") }

    Scaffold(Modifier.fillMaxSize(), topBar = {
        Column(Modifier.fillMaxWidth()) {
            TopAppBar(title = {
                Text(
                    modifier = Modifier,
                    text = "Filter Apps",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                )
            }, navigationIcon = {
                Icon(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    imageVector = FilterIcon,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary
                )
            })
            SearchBox(modifier = Modifier.padding(horizontal = 16.dp),
                hint = "Search Apps",
                onSearch = { query ->
                    searchQuery = query
                    vm.searchApps(query)
                })
            Spacer(modifier = Modifier.height(10.dp))
        }
    }, content = { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.secondary
                )
            } else {
                AppList(appList = if (searchQuery.isBlank()) {
                    appList
                } else {
                    searchList
                }, onSwitch = { enable, appPkg ->
                    vm.toggleDisableApp(enable, appPkg)
                })
            }
        }
    })
}

@Composable
private fun AppList(appList: List<AppItem>, onSwitch: (Boolean, String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 10.dp)
    ) {
        items(appList.size, key = { index -> appList[index].appPkg }) { index ->
            val item = appList[index]
            AppItemComposable(item, onSwitch = onSwitch)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun AppItemComposable(item: AppItem, onSwitch: (Boolean, String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(40.dp), bitmap = item.icon, contentDescription = ""
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.appName)
            Text(text = item.appPkg, fontSize = 12.sp)
        }
        Switch(modifier = Modifier.scale(.7f), checked = item.enabled, onCheckedChange = {
            onSwitch(it, item.appPkg)
        })
    }
}

@Composable
@Preview(showBackground = true)
private fun AppItem_Preview() {
    val appList = listOf(
        AppItem(
            appName = "NotificationHistory",
            appPkg = "com.nishantpardamwar.notificationhistory",
            icon = painterResource(id = R.drawable.ic_bell).toImageBitmap(),
            enabled = true,
        ),
        AppItem(
            appName = "WhatsApp",
            appPkg = "com.whatsapp",
            icon = painterResource(id = R.drawable.ic_bell).toImageBitmap(),
            enabled = false,
        ),
    )
    AppList(appList = appList, onSwitch = { _, _ ->

    })
}