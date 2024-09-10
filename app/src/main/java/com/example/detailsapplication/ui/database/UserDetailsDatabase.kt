package com.example.detailsapplication.ui.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.detailsapplication.ui.detailsForm.model.UserDetails

@Database(entities = [UserDetails::class], version = 1, exportSchema = false)
abstract class UserDetailsDatabase :RoomDatabase(){
    abstract fun getUserDetailsDao():UserDetailsDao

}