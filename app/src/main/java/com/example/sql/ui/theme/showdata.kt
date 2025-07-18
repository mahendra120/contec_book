package com.example.sql.ui.theme

import java.io.Serializable

data class User(
    val id: Int = 0,
    val name: String,
    val surname: String,
    val company: String,
    val mobile: String,
    val email: String,
    val address: String,
    val notes: String,
    val image: String
) :  Serializable