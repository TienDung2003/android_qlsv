package com.example.quan_ly_sv

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Student(
    val id: Int,
    val name: String,
    val mssv: String,
    val email: String,
    val phone: String
) : Parcelable
