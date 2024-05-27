package com.devan.lab.Utils

import com.devan.lab.Models.Course
import com.google.firebase.firestore.FirebaseFirestore


val firestore = FirebaseFirestore.getInstance()

fun saveCourses(courses: List<Course>) {
    val coursesCollection = firestore.collection("courses")

    for (course in courses) {
        val courseRef = coursesCollection.document(course.id.toString())
        courseRef.set(course).addOnSuccessListener {
            val modulesCollection = courseRef.collection("modules")
            for (module in course.modules) {
                modulesCollection.document(module.id.toString()).set(module)
            }
        }.addOnFailureListener { e ->
            e.printStackTrace()
        }
    }
}


