package com.drabazha.speakout.activity

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MotionEvent
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat
import com.drabazha.speakout.R
import com.drabazha.speakout.SpeakOutApplication
import com.drabazha.speakout.model.SentenceHolder
import com.drabazha.speakout.service.ConfigService
import com.drabazha.speakout.service.SentenceGeneratorApi
import com.drabazha.speakout.util.DatabaseConstants
import com.drabazha.speakout.util.SentenceApiConstants.SENTENCE_POOL_SIZE
import com.drabazha.speakout.util.TimerUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class GameActivity : CustomCompatActivity() {

    @Inject
    lateinit var sentenceGeneratorApi: SentenceGeneratorApi

    @Inject
    lateinit var gestureDetector: GestureDetectorCompat

    @Inject
    lateinit var configService: ConfigService

    private val coroutineContext = Dispatchers.IO

    private var job = Job()

    private var scope = CoroutineScope(coroutineContext + job)

    private lateinit var sentenceTextView: TextView

    private lateinit var alarmSound: MediaPlayer

    private var isSwipeLocked: Boolean = false

    private var roundTime: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        SpeakOutApplication.applicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        addArrowsAnimation()

        alarmSound = MediaPlayer.create(this, R.raw.alarm_sound)
        roundTime = configService.getConfig(DatabaseConstants.ConfigTableConstants.ConfigKeys.ROUND_TIME_SECONDS)

        sentenceTextView = findViewById(R.id.tvSentence)
        sentenceTextView.text = SentenceHolder.next().get()
        initializeTimer()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!isSwipeLocked) {
            gestureDetector.onTouchEvent(event)
        }
        sentenceTextView.text = SentenceHolder.get()
        if (SentenceHolder.needsRefill()) {
            scope.launch {
                val response = sentenceGeneratorApi.getSentences(SENTENCE_POOL_SIZE)
                if (response.isSuccessful) {
                    SentenceHolder.addAll(response.body()!!.sentences)
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun addArrowsAnimation() {
        val animation = TranslateAnimation(
            TranslateAnimation.ABSOLUTE, 0f,
            TranslateAnimation.ABSOLUTE, 0f,
            TranslateAnimation.RELATIVE_TO_PARENT, 0f,
            TranslateAnimation.RELATIVE_TO_PARENT, .01f)

        animation.duration = 1000
        animation.repeatCount = -1
        animation.repeatMode = Animation.REVERSE
        animation.interpolator = LinearInterpolator()

        findViewById<ImageView>(R.id.ivArrowsUp).animation = animation
    }

    private fun initializeTimer() {
        val timer = findViewById<TextView>(R.id.tvTimer)
        timer.text = TimerUtil.formatSeconds(roundTime)

        object: CountDownTimer((roundTime * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timer.text = TimerUtil.decreaseBy(timer.text.toString(), 1)
                if (millisUntilFinished < 3001) {
                    scope.launch {
                        withContext(Dispatchers.Main) {
                            timer.setTextColor(Color.parseColor("#f54242"))
                            addBlinkAnimationToTextView(timer)
                        }
                    }
                }
            }

            override fun onFinish() {
                isSwipeLocked = true
                timer.text = TimerUtil.formatSeconds(0)
                scope.launch {
                    withContext(Dispatchers.Main) {
                        alarmSound.start()
                    }
                }

                val intent = Intent(this@GameActivity, ScoreActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            }

        }.start()
    }
}