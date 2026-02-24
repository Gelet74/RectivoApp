package com.example.rectivoapp.modelo

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val username: String, val password: String)