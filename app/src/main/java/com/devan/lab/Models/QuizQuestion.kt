package com.devan.lab.Models

data class QuizQuestion(
    val id: Int = 0,
    val question: String,
    val options: List<String>,
    val correctAnswer: String
){
    constructor() : this(0, "", listOf(), "")
}
