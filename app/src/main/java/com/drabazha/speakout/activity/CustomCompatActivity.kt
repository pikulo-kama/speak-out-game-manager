package com.drabazha.speakout.activity

import android.util.TypedValue
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

open class CustomCompatActivity: AppCompatActivity() {

    fun addBlinkAnimationToTextView(view: TextView) {
        val animation: Animation = AlphaAnimation(.2f, 1f)
        animation.duration = 1000
        animation.startOffset = 20
        animation.repeatMode = Animation.REVERSE
        animation.repeatCount = Animation.INFINITE

        view.startAnimation(animation)
    }

    fun dpToPixels(dimensionId: Int): Int {
        val dp = resources.getDimension(dimensionId)
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, applicationContext.resources.displayMetrics).toInt()
    }
}