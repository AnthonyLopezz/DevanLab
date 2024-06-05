package com.devan.lab.Models

data class UserProgress(
    val courseId: Int = 0,
    val email: String,
    val completedModules: List<Int> = listOf(),
    val percentage: Double = 0.0,
    val userAnswers: Map<String, String> = mapOf(),
    val score: Int = 0
) {
    constructor() : this(0, "", listOf(), 0.0, mapOf(), 0)

    fun toMap(): Map<String, Any> = mapOf(
        "courseId" to courseId,
        "email" to email,
        "completedModules" to completedModules,
        "percentage" to percentage,
        "userAnswers" to userAnswers,
        "score" to score
    )
}
