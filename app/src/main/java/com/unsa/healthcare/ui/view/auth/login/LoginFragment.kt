package com.unsa.healthcare.ui.view.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.unsa.healthcare.R
import com.unsa.healthcare.databinding.FragmentLoginBinding
import com.unsa.healthcare.ui.view.auth.AuthActivity
import com.unsa.healthcare.ui.view.main.MainActivity
import com.unsa.healthcare.ui.viewmodel.auth.AuthViewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel: AuthViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val authActivity = activity as AuthActivity
        authViewModel = ViewModelProvider(authActivity)[AuthViewModel::class.java]
        initListeners()
        authViewModel.token.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Successful Login", Toast.LENGTH_LONG).show()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            (activity as AuthActivity).finish()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun initListeners() {
        binding.loginBtnSend.setOnClickListener { attemptLogin() }
        binding.loginTvRegister.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_registerFragment) }
    }
    private fun attemptLogin() {
        if (usernameInputIsValid() && passwordInputIsValid()) {
            val username = binding.loginEtUsername.text.toString()
            val password = binding.loginEtPassword.text.toString()
            authViewModel.login(username, password)
        }
    }
    private fun usernameInputIsValid(): Boolean {
        return checkTextInput(binding.loginEtUsername, binding.loginTilUsername, getString(R.string.username_required))
    }
    private fun passwordInputIsValid(): Boolean {
        return checkTextInput(binding.loginEtPassword, binding.loginTilPassword, getString(R.string.password_required))
    }
    private fun checkTextInput(inputText: TextInputEditText, inputLayout: TextInputLayout, errorMessage: String): Boolean {
        val isValid = inputText.text.toString().isNotBlank()
        inputLayout.error = if (isValid) null else errorMessage
        return isValid
    }
}