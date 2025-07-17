package com.example.sql

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    var screen by mutableStateOf("home1")
    var icon by mutableStateOf("hi")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = { MybottomBar() })
            { innerPadding ->
                Box(modifier = Modifier.padding())
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
        val bookIcon = Book_Icon().Book
        val bookicon = BookIcon().Book
        val parsonIcon = Contacs_Icon().Person

        val isDark = isSystemInDarkTheme()
        val backgroundColor = if (isDark) Color(20, 20, 20) else Color(245, 248, 250)
        val contentColor = if (isDark) Color.White else Color.Black

        BottomAppBar(containerColor  = backgroundColor)
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
                            modifier = Modifier.padding(start = 20.dp, top = 4.dp)
                        )
                    } else {
                        Icon(
                            parsonIcon,
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier.padding(start = 20.dp, top = 4.dp)
                        )
                    }
                    Text(text = "Contacts", modifier = Modifier.padding(top = 2.dp), color = contentColor)
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
                                .padding(start = 20.dp, top = 4.dp)
                                .size(20.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.book_white),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(contentColor),
                            modifier = Modifier
                                .padding(start = 20.dp, top = 4.dp)
                                .size(20.dp)
                        )
                    }
                    Text(text = "Organize", modifier = Modifier.padding(top = 4.dp), color = contentColor)
                }
            }
        }
    }

    @Composable
    @Preview(showSystemUi = true)
    fun MainScreen1() {
        val isDark = isSystemInDarkTheme()
        val backgroundColor = if (isDark) Color(20, 20, 20) else Color(245, 248, 250)
        val contentColor = if (isDark) Color.White else Color.Black
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(bottom = 120.dp, end = 20.dp)
        ) {
            FloatingActionButton(onClick = {
                val intent = Intent(this@MainActivity, HomePage::class.java)
                startActivity(intent)
            }, modifier = Modifier.align(Alignment.BottomEnd))
            {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(27.dp))
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
}

