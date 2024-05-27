package com.devan.lab.Models

data class User(
    val name: String,
    val email: String,
    val provider: String
) {
    fun toMap(): Map<String, Any> = mapOf("name" to name, "email" to email, "provider" to provider)

    constructor() : this("", "", "")
}