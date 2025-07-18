package com.example.sql.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

class Userpage : ComponentActivity() {

    var showImage: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        showImage = intent.getSerializableExtra("user") as? User

        setContent {
            Scaffold(modifier = Modifier,topBar = { MytopBar() }) { innerPadding ->
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding))
                {
                    MyHomePage()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    @Preview(showSystemUi = true)
    fun MytopBar() {

        val isDark = isSystemInDarkTheme()
        val backgroundColor = if (isDark) Color(20, 20, 20) else Color(245, 248, 250)
        val contentColor = if (isDark) Color.White else Color.Black

        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor),
            title = { Text("") },
            navigationIcon = {
                IconButton(onClick = { finish() }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp),
                        tint = contentColor
                    )
                }
            },
            actions = {
                IconButton(onClick = {}, modifier = Modifier.padding(end = 10.dp))
                {
                    Icon(Icons.Default.Edit, contentDescription = null, tint = contentColor)
                }
            }

        )
    }

    @Composable
    fun MyHomePage() {
        val isDark = isSystemInDarkTheme()
        val backgroundColor = if (isDark) Color(20, 20, 20) else Color(245, 248, 250)
        val contentColor = if (isDark) Color.White else Color.Black
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor)
        )
        {
            Image(
                painter = rememberImagePainter(data = showImage?.image),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .padding(20.dp)
            )
        }
    }
}
