package com.example.detailsapplication.ui.database

import com.example.detailsapplication.ui.detailsForm.model.UserDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserDetailsRepository @Inject constructor(private val userDetailsDao: UserDetailsDao) {

    fun insertUserDetails(userDetails: UserDetails) {
        userDetailsDao.insertDetails(userDetails)
    }
}