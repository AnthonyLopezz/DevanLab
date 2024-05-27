package com.devan.lab.Models

data class Module(
    val id: Int = 0,
    val title: String = "",
    val videoUrl: String = "",
    val description: String = ""
) {
    constructor() : this(0, "", "", "")
}