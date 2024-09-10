package com.example.detailsapplication.ui

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

const val DATABASE_NAME = "user_details_database"
const val USER_DETAILS_TABLE_NAME = "user_details"

enum class INPUT_FIELD{
    NAME,
    ADDRESS,
    AGE
}
fun convertLongToTime(time: Long?): String {
    if (time == null) return ""
    val date = Date(time)
    val format = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return format.format(date)
}