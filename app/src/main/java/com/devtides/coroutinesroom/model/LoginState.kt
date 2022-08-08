package com.devtides.coroutinesroom.model

object LoginState {
    var isLogin = false
    var user: User? = null
    fun logout() {
        isLogin = false
        user = null
    }

    fun login(user: User) {
        isLogin = true
        this.user = user
    }
}
