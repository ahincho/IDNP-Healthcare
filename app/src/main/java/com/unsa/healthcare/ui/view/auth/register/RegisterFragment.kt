package com.unsa.healthcare.ui.view.auth.register

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
import com.unsa.healthcare.data.network.dtos.auth.register.*
import com.unsa.healthcare.databinding.FragmentRegisterBinding
import com.unsa.healthcare.ui.view.auth.AuthActivity
import com.unsa.healthcare.ui.viewmodel.auth.AuthViewModel

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel: AuthViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val authActivity = activity as AuthActivity
        authViewModel = ViewModelProvider(authActivity)[AuthViewModel::class.java]
        initListeners()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun initListeners() {
        binding.registerBtnSend.setOnClickListener { saveUser() }
        binding.registerTvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
    private fun saveUser() {
        if (checkRegisterForm()) {
            val name = recoverTextInput(binding.registerEtName)
            val lastname = recoverTextInput(binding.registerEtLastname)
            val username = recoverTextInput(binding.registerEtUsername)
            val email = recoverTextInput(binding.registerEtEmail)
            val password = recoverTextInput(binding.registerEtPassword)
            authViewModel.register(RegisterRequest(name, lastname, username, email, password))
            Toast.makeText(context, "Registration was successful", Toast.LENGTH_SHORT).show()
        }
    }
    private fun recoverTextInput(inputEditText: TextInputEditText): String {
        return inputEditText.text.toString()
    }
    private fun checkRegisterForm(): Boolean {
        return checkTextInput(binding.registerEtName, binding.registerTilName, getString(R.string.name_required))
                && checkTextInput(binding.registerEtLastname, binding.registerTilLastname, getString(R.string.lastname_required))
                && checkTextInput(binding.registerEtUsername, binding.registerTilUsername, getString(R.string.username_required))
                && checkTextInput(binding.registerEtEmail, binding.registerTilEmail, getString(R.string.email_required))
                && checkTextInput(binding.registerEtPassword, binding.registerTilPassword, getString(R.string.password_required))
    }
    private fun checkTextInput(inputText: TextInputEditText, inputLayout: TextInputLayout, errorMessage: String): Boolean {
        val isValid = inputText.text.toString().isNotBlank()
        inputLayout.error = if (isValid) null else errorMessage
        return isValid
    }
}