package com.example.validator

fun validatePasswords(password1: String, password2: String): Pair<Boolean, String> {
    if (password1.length < 8) {
        return Pair(false, "The password must be at least 8 characters long.")
    }

    if (password1 != password2) {
        return Pair(false, "Passwords do not match.")
    }

    return Pair(true, "The passwords are valid and match.")
}


fun validateEmail(email: String): Pair<Boolean, String> {
    val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()
    return if (emailRegex.matches(email)) {
        Pair(true, "The email is valid.")
    } else {
        Pair(false, "The email is not valid.")
    }
}

fun validateInputs(name: String, email: String, password: String, rePassword: String): Pair<Boolean, String> {
    if (name.isEmpty() || email.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
        return Pair(false, "Please, fill out the information")
    }

    val (isValidEmail, emailMessage) = validateEmail(email)
    if (!isValidEmail) {
        return Pair(false, emailMessage)
    }

    val (isValidPassword, passwordMessage) = validatePasswords(password, rePassword)
    if (!isValidPassword) {
        return Pair(false, passwordMessage)
    }

    return Pair(true, "")
}