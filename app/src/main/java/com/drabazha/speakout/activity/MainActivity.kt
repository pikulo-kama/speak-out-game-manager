package com.drabazha.speakout.activity

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.drabazha.speakout.R

class MainActivity : CustomCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addBlinkAnimationToTextView(findViewById(R.id.tvTapOnScreenMessage))
        addBackgroundAnimation()
    }

    fun onOpenTeamsActivity(view: View) {
        val intent = Intent(this, TeamsActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    private fun addBackgroundAnimation() {
        val layer = findViewById<View>(R.id.vLayoutGradient)
        val animationDrawable = layer.background as AnimationDrawable

        animationDrawable.setEnterFadeDuration(10)
        animationDrawable.setExitFadeDuration(5000)
        animationDrawable.start()
    }
}