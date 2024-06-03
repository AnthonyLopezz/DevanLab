package com.devan.lab.Models

data class Module(
    val id: Int = 0,
    val title: String,
    val videoUrl: String,
    val description: String,
    val concepts: List<Concept> = listOf(),
    val quiz: List<QuizQuestion> = listOf()
){
    constructor() : this(0, "", "", "", listOf(), listOf())
}
