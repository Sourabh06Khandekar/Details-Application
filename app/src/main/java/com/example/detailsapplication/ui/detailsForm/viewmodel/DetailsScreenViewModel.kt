package com.example.detailsapplication.ui.detailsForm.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detailsapplication.ui.database.UserDetailsRepository
import com.example.detailsapplication.ui.detailsForm.model.UserDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(private val userDetailsRepository: UserDetailsRepository) :
    ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    private val _userAddress = MutableStateFlow("")
    val userAddress: StateFlow<String> = _userAddress

    private val _userAge = MutableStateFlow(0)
    val userAge: StateFlow<Int> = _userAge

    var initialDate: MutableState<Long?> = mutableStateOf(null)
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun setInitialDate(date: Long?) {
        initialDate.value = date
    }

    fun setUserName(userName: String) {
        _userName.value = userName
    }

    fun setUserAddress(userAddress: String) {
        _userAddress.value = userAddress
    }

    fun setUserAge(userAge: String) {
        _userAge.value = if (userAge.isNotEmpty()) {
            userAge.trim().toInt()
        } else {
            0
        }
    }

    fun isValidateForm():Boolean {
        return !(_userName.value.isEmpty() ||
                _userAddress.value.isEmpty() ||
                _userAge.value == 0 ||
                initialDate.value == null)
    }

    fun resetValues(){
        _userName.value=""
        _userAddress.value= ""
        _userAge.value =0
        initialDate.value=null
    }

    fun insertUserDetails() {
        coroutineScope.launch(Dispatchers.IO) {
          userDetailsRepository.insertUserDetails(
                UserDetails(
                    name = _userName.value,
                    address = _userAddress.value,
                    age = _userAge.value,
                    dateOfBirth = initialDate.value
                )
            )
        }
    }
}