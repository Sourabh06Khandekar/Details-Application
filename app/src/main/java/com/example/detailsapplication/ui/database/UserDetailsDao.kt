package com.example.detailsapplication.ui.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.detailsapplication.ui.detailsForm.model.UserDetails

@Dao
interface UserDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetails(userDetails: UserDetails)
}