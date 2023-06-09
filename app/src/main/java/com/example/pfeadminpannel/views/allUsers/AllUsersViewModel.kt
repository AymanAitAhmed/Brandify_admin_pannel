package com.example.pfeadminpannel.views.allUsers

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pfeadminpannel.data.ListSortingPreferences
import com.example.pfeadminpannel.data.localServerApi.LocalServerRepository
import com.example.pfeadminpannel.module.Response
import com.example.pfeadminpannel.module.localServer.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class AllUsersViewModel @Inject constructor(
    private val localServerRepository: LocalServerRepository,
    private val listSortingPreferences: ListSortingPreferences
) : ViewModel() {

    private val _allUsersList = MutableStateFlow<List<User>>(emptyList())
    val allUsersList = _allUsersList.asStateFlow()

    private val _sortingType = MutableStateFlow(ListSortingType.A_Z.stringType)
    val sortingType = _sortingType.asStateFlow()

    private val _isRefreshing = MutableStateFlow<Boolean>(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    val listIsLoading = mutableStateOf(false)

    init {
        getAllUsersList()
        viewModelScope.launch {
            getSortingType()
        }
    }

    fun getAllUsersList() {
        listIsLoading.value = true
        viewModelScope.launch {
            val getAllUsersResponse = localServerRepository.getAllUsers()

            when (getAllUsersResponse) {
                is Response.Failure -> getAllUsersResponse.apply {
                    listIsLoading.value = false
                    _errorMessage.value = "${e.message}"
                    Log.d("getAllUsers", "error: $e")
                }

                is Response.Loading -> TODO()
                is Response.Success -> {
                    listIsLoading.value = false
                    _allUsersList.value = getAllUsersResponse.data
                }
            }
        }
    }

    fun changeTheSortingType(sortingType: String) {
        viewModelScope.launch {
            listSortingPreferences.setSortingType(sortingType)
            getSortingType()
        }
    }

    suspend fun getSortingType() {
        listSortingPreferences.getSortingType.collect {
            _sortingType.value = it
        }
    }
}