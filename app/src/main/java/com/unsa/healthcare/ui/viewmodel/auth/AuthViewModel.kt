package com.unsa.healthcare.ui.viewmodel.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsa.healthcare.data.network.dtos.auth.login.LoginRequest
import com.unsa.healthcare.data.network.dtos.auth.login.LoginResponse
import com.unsa.healthcare.data.network.dtos.auth.register.RegisterRequest
import com.unsa.healthcare.data.network.dtos.auth.register.RegisterResponse
import com.unsa.healthcare.domain.auth.GetJwtTokenUseCase
import com.unsa.healthcare.domain.auth.LoginUseCase
import com.unsa.healthcare.domain.auth.RegisterUseCase
import com.unsa.healthcare.domain.auth.SaveJwtTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor (
    private val saveJwtTokenUseCase: SaveJwtTokenUseCase,
    private val getJwtTokenUseCase: GetJwtTokenUseCase,
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
): ViewModel() {
    val token = MutableLiveData<String>()
    val registerResponse = MutableLiveData<RegisterResponse>()
    fun login(username: String, password: String) {
        var response: LoginResponse?
        viewModelScope.launch {
            response = loginUseCase.invoke(LoginRequest(username, password))
            if (response != null) {
                token.postValue(response!!.token)
                saveJwtTokenUseCase(response!!.token)
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