package com.example.sql

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.sql.ui.theme.User
import java.io.ByteArrayOutputStream

class Adduser : ComponentActivity() {

    var editUser: User? = null

    var name by mutableStateOf("")
    var surname by mutableStateOf("")
    var company by mutableStateOf("")
    var notes by mutableStateOf("")
    var mobileNumber by mutableStateOf("")
    var Email by mutableStateOf("")
    var Address by mutableStateOf("")
    var imageStringData by mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        editUser = intent.getSerializableExtra("user1") as? User

        imageStringData = editUser?.image
        name = editUser?.name ?: ""
        surname = editUser?.surname ?: ""
        company = editUser?.company ?: ""
        notes = editUser?.notes ?: ""
        mobileNumber = editUser?.mobile ?: ""
        Email = editUser?.email ?: ""
        Address = editUser?.address ?: ""

        setContent {
            AppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { MyTopBar { finish() } }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        ContactBook()
                    }
                }
            }
        }
    }

    @Composable
    fun AppTheme(content: @Composable () -> Unit) {
        val isDark = isSystemInDarkTheme()
        val backgroundColor = if (isDark) Color.Black else Color.White

        MaterialTheme(
            colorScheme = if (isDark) darkColorScheme() else lightColorScheme()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
            ) {
                content()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyTopBar(onClose: () -> Unit) {
        val isDark = isSystemInDarkTheme()
        val backgroundColor = if (isDark) Color(20, 20, 20) else Color(245, 248, 250)
        val contentColor = if (isDark) Color.White else Color.Black

        TopAppBar(
            title = {
                Text(
                    "Create contact",
                    fontSize = 22.sp,
                    color = contentColor,
                    modifier = Modifier.padding(start = 5.dp)
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(25.dp)
                ) {
                    Icon(Icons.Default.Clear, contentDescription = null, tint = contentColor)
                }
            },
            actions = {
                Button(
                    onClick = {
                        val db = DataHelper(this@Adduser)
                        if (editUser != null) {
                            db.updateUser(
                                editUser!!.id,
                                name,
                                surname,
                                company,
                                mobileNumber,
                                Email,
                                Address,
                                notes,
                                imageStringData ?: ""
                            )
                            Toast.makeText(
                                this@Adduser,
                                "$name $surname Updated",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            name = ""
                            surname = ""
                            company = ""
                            mobileNumber = ""
                            Email = ""
                            Address = ""
                            notes = ""
                        } else {
                            db.insertData(
                                name,
                                surname,
                                company,
                                mobileNumber,
                                Email,
                                Address,
                                notes,
                                imageStringData ?: ""
                            )
                            Toast.makeText(this@Adduser, "$name $surname Save", Toast.LENGTH_SHORT)
                                .show()
                            name = ""
                            surname = ""
                            company = ""
                            mobileNumber = ""
                            Email = ""
                            Address = ""
                            notes = ""
                        }
                        val intent = Intent(this@Adduser, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    },
                    colors = ButtonDefaults.buttonColors(Color(41, 176, 236, 255)),
                    modifier = Modifier.padding(end = 10.dp)
                ) {
                    Text(text = "Save", fontSize = 14.sp)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor)
        )
    }

    @SuppressLint("SuspiciousIndentation")
    @Composable
    fun ContactBook() {
        val configuration = LocalConfiguration.current

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream) // Or JPEG
                val imageBytes = byteArrayOutputStream.toByteArray()
                imageStringData = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            }
        }


//        Log.d(
//            "hjhdfjs", "ContactBook: " +
//                    "name1: $name1, surname1: $surname1, email1: $email1, composable1: $composable1, number1: $number1"
//        )
//
//        if (name1 != null || surname1 != null || email1 != null || composable1 != null) {
//            company = composable1.toString()
//            Email = email1.toString()
//            surname = surname1.toString()
//            name = name1.toString()
//            mobileNumber = number1.toString()
//        }

        val isDark = isSystemInDarkTheme()
        val textColor = if (isDark) Color.White else Color.Black
        val backgroundColor = if (isDark) Color.Black else Color.White

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        onClick = {
                            launcher.launch("image/*")
                        },
                        shape = CircleShape,
                        modifier = Modifier.size(100.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = backgroundColor)
                    ) {
                        if (imageStringData != null) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    Base64.decode(
                                        imageStringData,
                                        Base64.DEFAULT
                                    )
                                ),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                Icons.Default.AccountCircle,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                tint = textColor
                            )
                        }
                    }
                }
            }
            item {
                Column(modifier = Modifier.padding(start = 45.dp, top = 20.dp)) {
                    CustomTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "First name"
                    )
                    CustomTextField(
                        value = surname,
                        onValueChange = { surname = it },
                        label = "Last name"
                    )
                    CustomTextField(
                        value = company,
                        onValueChange = { company = it },
                        label = "Company"
                    )

                    ExpandablePhoneInputCard()
                    ExpandableEmailInputCard()
                    ExpandableAddressInputCard()

                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Notes", color = textColor) },
                        modifier = Modifier
                            .height(120.dp)
                            .padding(top = 20.dp, bottom = 10.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun CustomTextField(value: String, onValueChange: (String) -> Unit, label: String) {
        val isDark = isSystemInDarkTheme()
        val textColor = if (isDark) Color.White else Color.Black

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label, color = textColor) },
            modifier = Modifier.padding(10.dp), maxLines = 1
        )
    }

    @Composable
    fun ExpandablePhoneInputCard() {
        var expanded by remember { mutableStateOf(false) }
        val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black

        Card(
            modifier = Modifier
                .padding(top = 20.dp, end = 5.dp, start = 10.dp)
                .clickable { expanded = !expanded }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row {
                    if (expanded) {
                        Icon(
                            Icons.Default.Call,
                            contentDescription = null,
                            tint = textColor,
                            modifier = Modifier.padding(end = 10.dp)
                        )
                    } else {
                        Icon(
                            Icons.Default.Call,
                            contentDescription = null,
                            tint = textColor,
                            modifier = Modifier.padding(end = 10.dp)
                        )
                    }
                    Text(
                        text = if (expanded) "Hide Phone Number" else "Add phone number",
                        color = textColor,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                AnimatedVisibility(visible = expanded) {
                    CustomTextField(
                        value = mobileNumber,
                        onValueChange = { mobileNumber = it },
                        label = "enter phone number"
                    )
                }
            }
        }
    }

    @Composable
    fun ExpandableEmailInputCard() {
        var expanded by remember { mutableStateOf(false) }
        val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black

        Card(
            modifier = Modifier
                .padding(top = 25.dp, end = 5.dp, start = 10.dp)
                .clickable { expanded = !expanded }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row {
                    if (expanded) {
                        Icon(
                            Icons.Default.MailOutline,
                            contentDescription = null,
                            tint = textColor,
                            modifier = Modifier.padding(end = 14.dp)
                        )
                    } else {
                        Icon(
                            Icons.Default.MailOutline,
                            contentDescription = null,
                            tint = textColor,
                            modifier = Modifier.padding(end = 14.dp)
                        )
                    }
                    Text(
                        text = if (expanded) "Hide email address" else "Add email address",
                        color = textColor,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                AnimatedVisibility(visible = expanded) {
                    CustomTextField(
                        value = Email,
                        onValueChange = { Email = it },
                        label = "enter email"
                    )
                }
            }
        }
    }

    @Composable
    fun ExpandableAddressInputCard() {
        var expanded by remember { mutableStateOf(false) }
        val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black

        Card(
            modifier = Modifier
                .padding(top = 20.dp, end = 5.dp, start = 10.dp)
                .clickable { expanded = !expanded }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row {
                    if (expanded) {
                        Icon(
                            Icons.Default.Place,
                            contentDescription = null,
                            tint = textColor,
                            modifier = Modifier.padding(end = 14.dp)
                        )
                    } else {
                        Icon(
                            Icons.Default.Place,
                            contentDescription = null,
                            tint = textColor,
                            modifier = Modifier.padding(end = 14.dp)
                        )
                    }
                    Text(
                        text = if (expanded) "Hide address" else "Add address",
                        color = textColor,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                AnimatedVisibility(visible = expanded) {
                    CustomTextField(
                        value = Address,
                        onValueChange = { Address = it },
                        label = "enter address"
                    )
                }
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun PreviewContactBook() {
        AppTheme {
            ContactBook()
        }
    }


}

/*
ExpandableAddressInputCard
 */
