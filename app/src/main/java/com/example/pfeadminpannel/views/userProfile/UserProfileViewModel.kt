package com.example.pfeadminpannel.views.userProfile

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pfeadminpannel.data.localServerApi.LocalServerRepository
import com.example.pfeadminpannel.module.Response
import com.example.pfeadminpannel.module.localServer.UserProfileInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val localServerRepository: LocalServerRepository
) : ViewModel() {

    private val _userProfileInfo = MutableStateFlow<UserProfileInfo>(
        UserProfileInfo(emptyList(), "", "", "", "", 0, 0, 0, 0, 0, 0, 0, emptyList())
    )
    val userProfileInfo = _userProfileInfo.asStateFlow()

    private val _isRefreshing = MutableStateFlow<Boolean>(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    val userUID = mutableStateOf("")

    val isLoading = mutableStateOf(false)

    val isScrollingUp = mutableStateOf(false)

    val userDeleted = mutableStateOf(false)

    val deleteButtonClicked = mutableStateOf(false)

    fun getUserProfileInfo(){
        isLoading.value = true
        viewModelScope.launch {
            val response = localServerRepository.getUserProfileInfo(userUID.value)
            when(response){
                is Response.Failure ->response.apply {
                    isLoading.value = false
                    Log.d("getUserProfileInfo", "error: $e")
                }
                is Response.Loading -> TODO()
                is Response.Success -> {
                    _userProfileInfo.value = response.data
                    isLoading.value = false
                }
            }
        }
    }

    fun deleteUser(){
        viewModelScope.launch {
            deleteButtonClicked.value = true
            val response = localServerRepository.deleteUserWithUid(userProfileInfo.value.userUID)
            when(response){
                is Response.Failure -> response.apply {
                    deleteButtonClicked.value = false
                    Log.d("deleteUser", "$e")
                }
                is Response.Loading -> TODO()
                is Response.Success -> {
                    deleteButtonClicked.value = false
                    if (response.data.deleted){
                        userDeleted.value = true
                    }
                }
            }
        }

    }

}