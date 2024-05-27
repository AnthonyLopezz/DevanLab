package com.devan.lab.Utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

 fun performClickAnimation(view: View) {
    val scaleXDown = ObjectAnimator.ofFloat(view, "scaleX", 0.9f)
    val scaleYDown = ObjectAnimator.ofFloat(view, "scaleY", 0.9f)
    val scaleXUp = ObjectAnimator.ofFloat(view, "scaleX", 1f)
    val scaleYUp = ObjectAnimator.ofFloat(view, "scaleY", 1f)

    scaleXDown.duration = 100
    scaleYDown.duration = 100
    scaleXUp.duration = 100
    scaleYUp.duration = 100

    val scaleDown = AnimatorSet()
    scaleDown.playTogether(scaleXDown, scaleYDown)

    val scaleUp = AnimatorSet()
    scaleUp.playTogether(scaleXUp, scaleYUp)

    val clickAnimation = AnimatorSet()
    clickAnimation.playSequentially(scaleDown, scaleUp)
    clickAnimation.start()
}
