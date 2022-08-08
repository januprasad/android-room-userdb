package com.devtides.coroutinesroom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devtides.coroutinesroom.model.LoginState
import com.devtides.coroutinesroom.model.User
import com.devtides.coroutinesroom.model.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupViewModel(application: Application) : AndroidViewModel(application) {

    val coScope = CoroutineScope(Dispatchers.IO)
    val db by lazy {
        UserDatabase(application).userDao()
    }
    val signupComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun signup(username: String, password: String, info: String) {
        coScope.launch {
            val user = db.getUser(username)
            if (user != null) {
                withContext(Dispatchers.Main) {
                    error.value = "User exists"
                }
            } else {
                val user = User(username, password.hashCode(), info)
                val id = db.insertUser(user)
                LoginState.login(user)
                withContext(Dispatchers.Main){
                    signupComplete.value = true
            }
        }
    }
}
}
