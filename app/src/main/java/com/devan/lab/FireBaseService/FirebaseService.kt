// FirebaseService.kt
package com.devan.lab.service

import UserProgress
import android.net.Uri
import android.util.Log
import com.devan.lab.Models.Course
import com.devan.lab.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID


class FirebaseService(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) {

    fun registerUser(user: User, password: String, callback: (Boolean, String?) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(user.email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firestore.collection("users").document(user.email).set(user.toMap())
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    fun uploadProfileImage(imageUri: Uri, callback: (String) -> Unit) {
        val fileName = UUID.randomUUID().toString()
        val ref = storage.getReference("/images/$fileName")
        ref.putFile(imageUri).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener { uri ->
                callback(uri.toString())
            }
        }
    }

    fun registerGoogleUser(user: User, callback: (Boolean, String?) -> Unit) {
            firestore.collection("users").document(user.email).set(user.toMap())
            callback(true, null)
    }

    fun getUserData(email: String, callback: (User?, String?) -> Unit) {
        firestore.collection("users").document(email).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(User::class.java)
                    callback(user, null)
                } else {
                    callback(null, "User not found")
                }
            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

    fun getAllCourses(callback: (List<Course>?, String?) -> Unit) {
        firestore.collection("courses")
            .get()
            .addOnSuccessListener { result ->
                val courses = result.map { document -> document.toObject(Course::class.java) }
                callback(courses, null)
            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }


    fun getUserProgress(email: String, courseId: Int, callback: (UserProgress?, String?) -> Unit) {
        val progressRef = firestore.collection("users").document(email).collection("progress")
            .document(courseId.toString())

        progressRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val progress = document.toObject(UserProgress::class.java)
                    if (progress != null) {
                        callback(progress, null)
                    } else {
                        createNewProgress(email, courseId, callback)
                    }
                } else {
                    createNewProgress(email, courseId, callback)
                }
            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

    fun getAllUserProgress(email: String, callback: (Map<Course, UserProgress>, String?) -> Unit) {
        val userProgressMap = mutableMapOf<Course, UserProgress>()

        firestore.collection("users").document(email).collection("progress").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val courseId = document.id.toInt()
                    val progress = document.toObject(UserProgress::class.java)

                    getCourseById(courseId.toString()) { course, error ->
                        if (course != null) {
                            userProgressMap[course] = progress
                        } else {
                            callback(emptyMap(), "Error fetching course data for progress")
                            return@getCourseById
                        }

                        if (userProgressMap.size == documents.size()) {
                            callback(userProgressMap, null)
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                callback(emptyMap(), exception.message)
            }
    }


    private fun createNewProgress(
        email: String,
        courseId: Int,
        callback: (UserProgress?, String?) -> Unit
    ) {
        val newProgress = UserProgress(courseId = courseId, email = email)
        val progressRef = firestore.collection("users").document(email).collection("progress")
            .document(courseId.toString())

        progressRef.set(newProgress)
            .addOnSuccessListener {
                callback(newProgress, null)
            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

    fun updateUserProgress(
        email: String,
        userProgress: UserProgress,
        callback: (Boolean, String?) -> Unit
    ) {
        val progressRef = firestore.collection("users").document(email).collection("progress")
            .document(userProgress.courseId.toString())

        progressRef.set(userProgress)
            .addOnSuccessListener {
                callback(true, null)
            }
            .addOnFailureListener { exception ->
                callback(false, exception.message)
            }
    }


    fun getCoursesByCategory(category: String, callback: (List<Course>?, String?) -> Unit) {
        firestore.collection("courses")
            .whereEqualTo("category", category)
            .get()
            .addOnSuccessListener { result ->
                val courses = result.map { document -> document.toObject(Course::class.java) }
                callback(courses, null)
            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

    fun getCourseById(id: String, callback: (Course?, String?) -> Unit) {
        firestore.collection("courses")
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                val course = document.toObject(Course::class.java)
                callback(course, null)
            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

    fun getFirstCompletedCourse(
        email: String,
        callback: (Course?, UserProgress?, String?) -> Unit
    ) {
        firestore.collection("users").document(email).collection("progress")
            .limit(1)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val progress = querySnapshot.documents[0].toObject(UserProgress::class.java)
                    if (progress != null) {
                        firestore.collection("courses").document(progress.courseId.toString())
                            .get()
                            .addOnSuccessListener { courseDocument ->
                                val course = courseDocument.toObject(Course::class.java)
                                callback(course, progress, null)
                            }
                            .addOnFailureListener { exception ->
                                callback(null, null, exception.message)
                            }
                    } else {
                        callback(null, null, "No progress found")
                    }
                } else {
                    callback(null, null, "No completed courses found")
                }
            }
            .addOnFailureListener { exception ->
                callback(null, null, exception.message)
            }
    }


}
