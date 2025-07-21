package com.example.sql

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.sql.ui.theme.Userpage

class MainActivity : ComponentActivity() {
    var screen by mutableStateOf("home1")
    var icon by mutableStateOf("hi")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = { MybottomBar() })
            { _ ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding()
                )
                {
                    when (screen) {
                        "home1" -> MainScreen1()
                        "home2" -> MainScreen2()
                    }
                }
            }
        }
    }

    @Composable
    fun MybottomBar() {
        val parsonIcon = Contacs_Icon().Person
        val isDark = isSystemInDarkTheme()
        val backgroundColor = if (isDark) Color(20, 20, 20) else Color(245, 248, 250)
        val contentColor = if (isDark) Color.White else Color.Black

        BottomAppBar(containerColor = backgroundColor)
        {
            IconButton(onClick = {
                screen = "home1"
                icon = "hi"
            }, modifier = Modifier.weight(1f))
            {
                Column {
                    if (icon == "hi") {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier.padding(start = 20.dp, top = 0.dp)
                        )
                    } else {
                        Icon(
                            parsonIcon,
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier.padding(start = 20.dp, top = 0.dp)
                        )
                    }
                    Text(
                        text = "Contacts",
                        fontSize = 15.sp,
                        modifier = Modifier.padding(top = 2.dp),
                        color = contentColor
                    )
                }
            }
            IconButton(onClick = {
                screen = "home2"
                icon = "hello"
            }, modifier = Modifier.weight(1f))
            {
                Column {
                    if (icon == "hello") {
                        Image(
                            painter = painterResource(R.drawable.book_black),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(contentColor),
                            modifier = Modifier
                                .padding(start = 20.dp, top = 0.dp)
                                .size(20.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.book_white),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(contentColor),
                            modifier = Modifier
                                .padding(start = 20.dp, top = 0.dp)
                                .size(20.dp)
                        )
                    }
                    Text(
                        text = "Organize",
                        fontSize = 15.sp,
                        modifier = Modifier.padding(top = 2.dp),
                        color = contentColor
                    )
                }
            }
        }
    }

    @Composable
    @Preview(showSystemUi = true)
    fun MainScreen1() {
        val db = DataHelper(this@MainActivity)
        val list = db.viewdata()

        val isDark = isSystemInDarkTheme()
        val backgroundColor = if (isDark) Color(20, 20, 20) else Color(245, 248, 250)
        val contentColor = if (isDark) Color.White else Color.Black
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 65.dp, start = 0.dp, end = 20.dp, bottom = 80.dp)
            ) {
                items(list.size) { index ->
                    val user = list[index]
                    Card(
                        onClick = {
                            val intent = Intent(this@MainActivity, Userpage::class.java)
                            intent.putExtra("name",user.name)
                            intent.putExtra("surname",user.surname)
                            intent.putExtra("company",user.company)
                            intent.putExtra("number",user.mobile)
                            intent.putExtra("email",user.email)
                            startActivity(intent)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp)
                            .padding(bottom = 5.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp)
                        ) {
                            @Composable
                            fun UserImageFromBase64(base64Image: String) {
                                Card(
                                    modifier = Modifier
                                        .size(size = 50.dp)
                                        .clip(CircleShape),
                                    colors = CardDefaults.cardColors(
                                        contentColor.copy(.5f)
                                    )
                                ) {
                                    if (base64Image != "null" && base64Image.isNotEmpty()) {
                                        Image(
                                            painter = rememberAsyncImagePainter(
                                                Base64.decode(
                                                    base64Image,
                                                    Base64.DEFAULT
                                                )
                                            ),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(50.dp),
                                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                        )
                                    } else {
                                        Text(
                                            text = user.name[0].toString().uppercase(),
                                            fontSize = 23.sp,
                                            color = Color.Yellow, modifier = Modifier
                                                .align(
                                                    Alignment.CenterHorizontally
                                                )
                                                .padding(top = 12.dp)
                                        )
                                        intent.putExtra("contect",user.name[0].toString().uppercase())
                                        Log.d("lkdkodjojfd", "UserImageFromBase64: ${user.name[0].toString().uppercase()}")
                                    }
                                }
                            }

                            UserImageFromBase64(user.image)
                            intent.putExtra("image", "$user.image")
                            Log.d("jiuyui", "MainScreen1: ${user.image}")
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 22.dp, top = 10.dp)
                            ) {
                                Text(
                                    user.name,
                                    fontSize = 23.sp,
                                    color = contentColor,
                                    modifier = Modifier.padding(end = 7.dp)
                                )
                                Text(
                                    text = user.surname,
                                    fontSize = 23.sp,
                                    color = contentColor
                                )
                            }
                            intent.putExtra("name",user.name)
                            intent.putExtra("surname",user.surname)
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 110.dp, end = 20.dp)
            ) {
                FloatingActionButton(onClick = {
                    val intent = Intent(this@MainActivity, Adduser::class.java)
                    startActivity(intent)
                }, modifier = Modifier.align(Alignment.BottomEnd))
                {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(27.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen2() {
    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color(20, 20, 20) else Color(245, 248, 250)
    val contentColor = if (isDark) Color.White else Color.Black
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Text(text = "hello", fontSize = 50.sp, color = contentColor)
    }
}


