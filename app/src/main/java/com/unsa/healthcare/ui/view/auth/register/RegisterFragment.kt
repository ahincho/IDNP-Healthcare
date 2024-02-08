package com.unsa.healthcare.ui.view.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.unsa.healthcare.R
import com.unsa.healthcare.core.checkTextInput
import com.unsa.healthcare.core.recoverTextInput
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
        binding.registerBtnSend.setOnClickListener { attemptRegister() }
        binding.registerTvLogin.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    private fun attemptRegister() {
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
    private fun checkRegisterForm(): Boolean {
        val nameIsValid = checkTextInput(binding.registerEtName, binding.registerTilName, getString(R.string.name_required))
        val lastnameIsValid = checkTextInput(binding.registerEtLastname, binding.registerTilLastname, getString(R.string.lastname_required))
        val usernameIsValid = checkTextInput(binding.registerEtUsername, binding.registerTilUsername, getString(R.string.username_required))
        val emailIsValid = checkTextInput(binding.registerEtEmail, binding.registerTilEmail, getString(R.string.email_required))
        val passwordIsValid = checkTextInput(binding.registerEtPassword, binding.registerTilPassword, getString(R.string.password_required))
        return nameIsValid && lastnameIsValid && usernameIsValid && emailIsValid && passwordIsValid
    }
}