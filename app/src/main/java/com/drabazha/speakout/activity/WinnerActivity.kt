package com.drabazha.speakout.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.drabazha.speakout.R
import com.drabazha.speakout.model.GameManager

class WinnerActivity : CustomCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winner)

        val winnerNameView = findViewById<TextView>(R.id.tvWinnerName)
        val winnerPointsView = findViewById<TextView>(R.id.tvWinnerPoints)

        winnerNameView.text = GameManager.getWinner().teamName
        winnerPointsView.text = "${GameManager.getWinner().teamScore} pts"

        GameManager.reset()
    }

    fun onClickMainMenu(view: View) {
        val intent = Intent(this, TeamsActivity::class.java)
        startActivity(intent)
    }
}