data class UserProgress(
    val courseId: Int = 0,
    val email: String,
    val completedModules: List<Int> = listOf(),
    val percentage: Double = 0.0
) {
    constructor() : this(0, "", listOf(), 0.0)

    fun toMap(): Map<String, Any> = mapOf(
        "courseId" to courseId,
        "email" to email,
        "completedModules" to completedModules,
        "percentage" to percentage
    )


    fun calculatePercentage(totalModules: Int): Double {
        return (completedModules.size.toDouble() / totalModules.toDouble()) * 100
    }
}
