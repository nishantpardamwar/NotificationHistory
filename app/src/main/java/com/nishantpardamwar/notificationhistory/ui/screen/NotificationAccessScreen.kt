package com.nishantpardamwar.notificationhistory.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nishantpardamwar.notificationhistory.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotificationAccessScreen(onAllowClick: () -> Unit) {
    Scaffold(Modifier.fillMaxSize(), topBar = {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_bell),
                contentDescription = "BellIcon"
            )
            Spacer(modifier = Modifier.padding(horizontal = 10.dp))
            Text(
                text = "Notification Manager",
                fontSize = 20.sp,
                color = colorResource(id = R.color.color_0078E7),
                fontWeight = FontWeight.Bold
            )
        }
    }) {
        Box(Modifier.fillMaxSize(), Alignment.Center) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), Arrangement.Center, Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp),
                    text = "Please Provide notifications access to be able to use the App",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.padding(vertical = 25.dp))
                Button(
                    onClick = onAllowClick,
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.color_0078E7))
                ) {
                    Text(text = "Allow", color = Color.White)
                }
            }
        }
    }
}