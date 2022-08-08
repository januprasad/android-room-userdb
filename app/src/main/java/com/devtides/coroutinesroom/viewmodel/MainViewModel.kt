package com.devtides.coroutinesroom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devtides.coroutinesroom.model.LoginState
import com.devtides.coroutinesroom.model.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val coScope = CoroutineScope(Dispatchers.IO)
    val db by lazy {
        UserDatabase(application).userDao()
    }
    val userDeleted = MutableLiveData<Boolean>()
    val signout = MutableLiveData<Boolean>()

    fun onSignout() {
        LoginState.logout()
        signout.value = true
    }

    fun onDeleteUser() {
        coScope.launch {
            LoginState.user?.let { user ->
                db.deleteUser(user.id)
            }
            withContext(Dispatchers.Main) {
                LoginState.logout()
                signout.value = true
                userDeleted.value = true
            }
        }
    }
}
