package com.example.sql.ui.theme

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

import com.example.sql.R
import androidx.core.net.toUri
import com.example.sql.Adduser
import com.example.sql.DataHelper

class Userpage : ComponentActivity() {

    var editUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        editUser = intent.getSerializableExtra("user") as? User

        setContent {
            Scaffold(modifier = Modifier, topBar = { MytopBar() }) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
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
                IconButton(onClick = {
                    val intent = Intent(this@Userpage, Adduser::class.java)
                    intent.putExtra("user1", editUser)
                    startActivity(intent)
                }, modifier = Modifier.padding(end = 10.dp))
                {
                    Icon(Icons.Default.Edit, contentDescription = null, tint = contentColor)
                }
                Button(
                    onClick = {
                        if (editUser != null) {
                            var db = DataHelper(this@Userpage)
                            db.deleteUser(editUser!!.id)
                            Toast.makeText(
                                this@Userpage,
                                "${editUser!!.name} ${editUser!!.surname} Deleted",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                ) {
                    androidx.compose.material3.Text(
                        "delete",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        )
    }

    @Composable
    fun MyHomePage() {
        val isDark = isSystemInDarkTheme()
        val backgroundColor = if (isDark) Color(20, 20, 20) else Color(245, 248, 250)
        val contentColor = if (isDark) Color.White else Color.Black

        var name by remember { mutableStateOf(editUser?.name ?: "") }
        var surname by remember { mutableStateOf(editUser?.surname ?: "") }
        var company by remember { mutableStateOf(editUser?.company ?: "") }
        var number by remember { mutableStateOf(editUser?.mobile ?: "") }
        var email by remember { mutableStateOf(editUser?.email ?: "") }
        val context = LocalContext.current


        Log.d("90988786756", "MyHomePage: $name $surname $company $number $email")
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        )
        {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    @Composable
                    fun UserImageFromBase64(base64Image: String) {
                        Card(
                            modifier = Modifier
                                .size(size = 170.dp)
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
                                        .size(300.dp),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Text(
                                    text = editUser?.name[0].toString().uppercase(),
                                    fontSize = 90.sp, fontWeight = FontWeight.Bold,
                                    color = Color.Yellow, modifier = Modifier
                                        .align(
                                            Alignment.CenterHorizontally
                                        )
                                        .padding(top = 38.dp)
                                )
                            }
                        }
                    }
                    UserImageFromBase64(editUser?.image ?: "")

                    Row(modifier = Modifier.padding(top = 20.dp)) {
                        Text(
                            name.toString(),
                            fontSize = 28.sp,
                            color = contentColor,
                            modifier = Modifier.padding(end = 7.dp)
                        )
                        Text(
                            surname.toString(),
                            fontSize = 28.sp,
                            color = contentColor,
                            modifier = Modifier.padding(start = 7.dp)
                        )
                    }
                    Text(
                        company.toString(),
                        fontSize = 15.sp,
                        color = contentColor.copy(.7f),
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(
                            modifier = Modifier,
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            FloatingActionButton(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_DIAL).apply {
                                        data = "tel:${editUser?.mobile}".toUri()
                                    }
                                    context.startActivity(intent)
                                },
                                shape = CircleShape,
                                modifier = Modifier
                                    .size(50.dp)
                                    .border(1.dp, contentColor, CircleShape),
                                containerColor = backgroundColor,
                                contentColor = contentColor
                            )
                            {
                                Icon(
                                    Icons.Default.Call,
                                    contentDescription = null,
                                    tint = contentColor
                                )
                            }
                            Text(
                                "Call",
                                fontSize = 13.sp,
                                color = contentColor,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                        }
                        Column(
                            modifier = Modifier,
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            FloatingActionButton(
                                onClick = { },
                                shape = CircleShape,
                                modifier = Modifier
                                    .size(50.dp)
                                    .border(1.dp, contentColor, CircleShape),
                                containerColor = backgroundColor,
                                contentColor = contentColor
                            )
                            {
                                Image(
                                    painter = painterResource(R.drawable.message),
                                    contentDescription = null, modifier = Modifier.size(22.dp),
                                    colorFilter = ColorFilter.tint(contentColor),
                                )
                            }
                            Text(
                                "Massage",
                                fontSize = 13.sp,
                                color = contentColor,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                        }
                        Column(
                            modifier = Modifier,
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            FloatingActionButton(
                                onClick = { },
                                shape = CircleShape,
                                modifier = Modifier
                                    .size(50.dp)
                                    .border(1.dp, contentColor, CircleShape),
                                containerColor = backgroundColor,
                                contentColor = contentColor
                            )
                            {
                                Image(
                                    painter = painterResource(R.drawable.video),
                                    contentDescription = null, modifier = Modifier.size(25.dp),
                                    colorFilter = ColorFilter.tint(contentColor),
                                )
                            }
                            Text(
                                "Video",
                                fontSize = 13.sp,
                                color = contentColor,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(12.dp))
                    if (number == "") {
                        Card(
                            onClick = {
                                val intent = Intent(this@Userpage, Adduser::class.java)
                                intent.putExtra("user1", editUser)
                                startActivity(intent)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(170.dp)
                                .padding(13.dp),
                            colors = CardDefaults.cardColors(containerColor = contentColor.copy(.2f))
                        ) {
                            Text(
                                "Contact info",
                                fontSize = 17.sp,
                                modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                                color = contentColor
                            )
                            Spacer(modifier = Modifier.padding(11.dp))
                            Row {
                                Icon(
                                    Icons.Default.Call,
                                    contentDescription = null,
                                    modifier = Modifier.padding(start = 15.dp, end = 10.dp),
                                    tint = contentColor
                                )
                                Text(
                                    "Add phone number",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(start = 5.dp, top = 5.dp),
                                    color = contentColor
                                )
                            }
                            Spacer(modifier = Modifier.padding(13.dp))
                            Row {
                                Icon(
                                    Icons.Default.MailOutline,
                                    contentDescription = null,
                                    modifier = Modifier.padding(start = 15.dp, end = 10.dp),
                                    tint = contentColor
                                )
                                Text(
                                    "Add phone email",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(start = 5.dp, top = 5.dp),
                                    color = contentColor
                                )
                            }
                        }
                    } else {
                        if (email != "") {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(190.dp)
                                    .padding(13.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = contentColor.copy(
                                        .2f
                                    )
                                )
                            )
                            {
                                Text(
                                    "Contact info",
                                    fontSize = 17.sp,
                                    modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                                    color = contentColor
                                )
                                Spacer(modifier = Modifier.padding(10.dp))
                                Row(modifier = Modifier.padding(start = 13.dp)) {
                                    Icon(
                                        Icons.Default.Call,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(48.dp)
                                            .padding(10.dp), tint = contentColor
                                    )
                                    Column(Modifier.padding(start = 7.dp, top = 5.dp)) {
                                        Text(
                                            number.toString(),
                                            fontSize = 17.sp,
                                            color = contentColor
                                        )
                                        Spacer(modifier = Modifier.padding(2.dp))
                                        Text(
                                            "Mobile",
                                            fontSize = 14.sp,
                                            color = contentColor,
                                            modifier = Modifier.padding(start = 10.dp, top = 3.dp),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                if (email != "") {
                                    Spacer(modifier = Modifier.padding(4.dp))
                                    Row(modifier = Modifier.padding(start = 13.dp)) {
                                        Icon(
                                            Icons.Default.MailOutline,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(48.dp)
                                                .padding(10.dp), tint = contentColor
                                        )
                                        Column(Modifier.padding(start = 7.dp, top = 5.dp)) {
                                            Text(
                                                email.toString(),
                                                fontSize = 17.sp,
                                                color = contentColor
                                            )
                                            Spacer(modifier = Modifier.padding(2.dp))
                                            Text(
                                                "Email",
                                                fontSize = 14.sp,
                                                color = contentColor,
                                                modifier = Modifier.padding(
                                                    start = 10.dp,
                                                    top = 3.dp
                                                ),
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            }
                        } else {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(130.dp)
                                    .padding(13.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = contentColor.copy(
                                        .2f
                                    )
                                )
                            )
                            {
                                Text(
                                    "Contact info",
                                    fontSize = 17.sp,
                                    modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                                    color = contentColor
                                )
                                Spacer(modifier = Modifier.padding(10.dp))
                                Row(modifier = Modifier.padding(start = 13.dp)) {
                                    Icon(
                                        Icons.Default.Call,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(48.dp)
                                            .padding(10.dp), tint = contentColor
                                    )
                                    Column(Modifier.padding(start = 7.dp, top = 5.dp)) {
                                        Text(
                                            number.toString(),
                                            fontSize = 17.sp,
                                            color = contentColor
                                        )
                                        Spacer(modifier = Modifier.padding(2.dp))
                                        Text(
                                            "Mobile",
                                            fontSize = 14.sp,
                                            color = contentColor,
                                            modifier = Modifier.padding(start = 10.dp),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(1.dp))
                        if (company != "") {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(130.dp)
                                    .padding(13.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = contentColor.copy(
                                        .2f
                                    )
                                )
                            )
                            {
                                Text(
                                    "About ${name}", fontSize = 17.sp,
                                    modifier = Modifier.padding(start = 25.dp, top = 15.dp),
                                    color = contentColor
                                )
                                Row(modifier = Modifier.padding(start = 25.dp, top = 25.dp)) {
                                    Image(
                                        painter = painterResource(R.drawable.office),
                                        contentDescription = null, modifier = Modifier.size(25.dp),
                                        colorFilter = ColorFilter.tint(contentColor),
                                    )
                                    Text(
                                        company.toString(), fontSize = 15.sp,
                                        color = contentColor,
                                        modifier = Modifier.padding(start = 15.dp, top = 4.dp),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
