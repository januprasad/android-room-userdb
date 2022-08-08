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

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    val loginComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    val coScope = CoroutineScope(Dispatchers.IO)
    val db by lazy {
        UserDatabase(application).userDao()
    }

    fun login(username: String, password: String) {
        coScope.launch {
            val user = db.getUser(username)
            if (user == null) {
                withContext(Dispatchers.Main) {
                    error.value = "user not found"
                }
            } else {
                if (user.passwordHash == password.hashCode()) {
                    loginComplete.value = true
                    LoginState.login(user)
                } else {
                    error.value = "Invalid password"
                }
            }
        }
    }
}
