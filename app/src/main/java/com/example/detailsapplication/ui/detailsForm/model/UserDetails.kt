package com.example.detailsapplication.ui.detailsForm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.detailsapplication.ui.USER_DETAILS_TABLE_NAME

@Entity(tableName = USER_DETAILS_TABLE_NAME)
data class UserDetails(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    @ColumnInfo(name = "user_name")
    val name:String,
    @ColumnInfo(name = "user_address")
    val address:String,
    @ColumnInfo(name = "user_age")
    val age:Int,
    @ColumnInfo(name = "user_date_of_birth")
    val dateOfBirth:Long?
)
