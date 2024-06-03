package com.devan.lab.Models

data class Concept(
    val id: Int = 0,
    val title: String,
    val category: String,
    val description: String
)
{
    constructor() : this(0, "", "", "")
}
