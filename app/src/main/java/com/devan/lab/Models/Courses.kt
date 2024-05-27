package com.devan.lab.Models


data class Course(
    val id: Int = 0,
    val title: String,
    val icon: String,
    val category: String,
    val modules: List<Module> = listOf()
) {

    constructor() : this(0, "", "", "", listOf())
}
