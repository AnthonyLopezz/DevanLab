package com.devan.lab.Models

import android.os.Parcel
import android.os.Parcelable
data class QuizQuestion(
    val id: Int = 0,
    val question: String?,
    val options: List<String>?,
    val correctAnswer: String?,
    val score: Int,
    var clickedAnswer: String?
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    constructor() : this(0, "", listOf(), "", 0, "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(question)
        parcel.writeStringList(options)
        parcel.writeString(correctAnswer)
        parcel.writeInt(score)
        parcel.writeString(clickedAnswer)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuizQuestion> {
        override fun createFromParcel(parcel: Parcel): QuizQuestion {
            return QuizQuestion(parcel)
        }

        override fun newArray(size: Int): Array<QuizQuestion?> {
            return arrayOfNulls(size)
        }
    }
}
