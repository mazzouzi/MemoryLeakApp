package com.mazzouzi.memoryleak.ui.basefragment

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel : ViewModel() {

    private val _userState = MutableStateFlow(UserEnum.UNDEFINED)
    val userState = _userState.asStateFlow()

    fun onUserSelected(value: UserEnum) {
        _userState.value = value
    }
}

enum class UserEnum {
    UNDEFINED,
    ADMIN,
    SUPER_USER,
    USER
}