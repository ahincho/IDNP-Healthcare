package com.unsa.healthcare.ui.viewmodel.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsa.healthcare.data.network.dtos.auth.login.LoginRequest
import com.unsa.healthcare.data.network.dtos.auth.register.RegisterRequest
import com.unsa.healthcare.data.network.dtos.auth.register.RegisterResponse
import com.unsa.healthcare.data.repositories.PreferencesRepository.Companion.JWT
import com.unsa.healthcare.data.repositories.PreferencesRepository.Companion.PASSWORD
import com.unsa.healthcare.data.repositories.PreferencesRepository.Companion.USERNAME
import com.unsa.healthcare.domain.auth.login.LoginUseCase
import com.unsa.healthcare.domain.auth.register.RegisterUseCase
import com.unsa.healthcare.domain.auth.login.SavePreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor (
    private val loginUseCase: LoginUseCase,
    private val savePreferenceUseCase: SavePreferenceUseCase,
    private val registerUseCase: RegisterUseCase
): ViewModel() {
    val token = MutableLiveData<String>()
    private val registerResponse = MutableLiveData<RegisterResponse>()
    fun login(username: String, password: String, remember: Boolean) {
        viewModelScope.launch {
            val response = loginUseCase.invoke(LoginRequest(username, password)) ?: return@launch
            token.postValue(response.token)
            savePreferenceUseCase(JWT, response.token)
            if (remember) {
                savePreferenceUseCase(USERNAME, username)
                savePreferenceUseCase(PASSWORD, password)
            }
        }
    }
    fun register(registerRequest: RegisterRequest) {
        var response: RegisterResponse?
        viewModelScope.launch {
            response = registerUseCase.invoke(registerRequest)
            if (response != null) {
                registerResponse.postValue(response!!)
            }
        }
    }
}