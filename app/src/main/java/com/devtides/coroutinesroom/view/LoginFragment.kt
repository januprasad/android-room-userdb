package com.devtides.coroutinesroom.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.devtides.coroutinesroom.R
import com.devtides.coroutinesroom.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginBtn.setOnClickListener { onLogin(it) }
        gotoSignupBtn.setOnClickListener { onGotoSignup(it) }

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loginComplete.observe(
            this,
            Observer { isComplete ->
                loginSuccess()
            }
        )

        viewModel.error.observe(
            this,
            Observer { error ->
            }
        )
    }

    private fun loginSuccess() {
        val action = LoginFragmentDirections.actionGoToMain()
        Navigation.findNavController(loginUsername).navigate(action)
    }

    private fun onLogin(v: View) {
        val username = loginUsername.text.toString()
        val password = loginPassword.text.toString()
        if (username.isEmpty() ||
            password.isEmpty()
        ) {
            showToast("Please fill")
        } else {
            viewModel.login(username, password)
        }
    }

    private fun showToast(s: String) {
        Toast.makeText(
            activity,
            s,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun onGotoSignup(v: View) {
        val action = LoginFragmentDirections.actionGoToSignup()
        Navigation.findNavController(v).navigate(action)
    }
}
