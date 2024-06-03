package com.devan.lab.Utils

import com.devan.lab.Models.Course
import com.google.firebase.firestore.FirebaseFirestore


val firestore = FirebaseFirestore.getInstance()

fun saveCourses(courses: List<Course>) {
    val coursesCollection = firestore.collection("courses")

    for (course in courses) {
        val courseRef = coursesCollection.document(course.id.toString())
        courseRef.set(course)
            .addOnSuccessListener {
                val modulesCollection = courseRef.collection("modules")
                for (module in course.modules) {
                    val moduleRef = modulesCollection.document(module.id.toString())
                    moduleRef.set(module)
                        .addOnSuccessListener {
                            val conceptsCollection = moduleRef.collection("concepts")
                            for (concept in module.concepts) {
                                conceptsCollection.document(concept.id.toString()).set(concept)
                            }

                            val quizCollection = moduleRef.collection("quiz")
                            for (question in module.quiz) {
                                quizCollection.document(question.id.toString()).set(question)
                            }
                        }
                        .addOnFailureListener { e ->
                            e.printStackTrace()
                        }
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}


