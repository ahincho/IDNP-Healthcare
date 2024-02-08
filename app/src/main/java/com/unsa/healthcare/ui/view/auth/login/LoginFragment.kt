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
import com.unsa.healthcare.R
import com.unsa.healthcare.core.checkTextInput
import com.unsa.healthcare.core.recoverTextInput
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
            Toast.makeText(context, "Login was successful!", Toast.LENGTH_LONG).show()
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
        val fieldsAreValid = checkAllFields()
        if (fieldsAreValid) {
            val username = recoverTextInput(binding.loginEtUsername)
            val password = recoverTextInput(binding.loginEtPassword)
            val remember = binding.loginCbRemember.isChecked
            authViewModel.login(username, password, remember)
        }
    }

    private fun checkAllFields(): Boolean {
        val usernameIsValid = checkTextInput(binding.loginEtUsername, binding.loginTilUsername, getString(R.string.username_required))
        val passwordIsValid = checkTextInput(binding.loginEtPassword, binding.loginTilPassword, getString(R.string.password_required))
        return usernameIsValid && passwordIsValid
    }
}