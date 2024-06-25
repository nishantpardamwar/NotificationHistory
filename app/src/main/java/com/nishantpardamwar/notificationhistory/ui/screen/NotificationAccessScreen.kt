package com.nishantpardamwar.notificationhistory.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nishantpardamwar.notificationhistory.R

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotificationAccessScreen(onAllowClick: () -> Unit) {
    Scaffold(Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            Text(
                text = "Notification History",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
        }, navigationIcon = {
            Image(
                modifier = Modifier.padding(horizontal = 10.dp),
                painter = painterResource(id = R.drawable.ic_bell),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                contentDescription = "BellIcon"
            )
        })
    }, content = { paddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues), Alignment.Center
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                Arrangement.Center,
                Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp),
                    text = "Please Provide notifications access to be able to use the App",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.padding(vertical = 25.dp))
                Button(
                    onClick = onAllowClick,
                ) {
                    Text(text = "Allow")
                }
            }
        }
    })
}