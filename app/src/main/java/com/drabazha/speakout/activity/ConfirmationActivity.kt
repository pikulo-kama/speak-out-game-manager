package com.drabazha.speakout.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.drabazha.speakout.R
import com.drabazha.speakout.model.GameManager
import com.drabazha.speakout.model.TeamHolder
import org.w3c.dom.Text

class ConfirmationActivity : CustomCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)


        val currentTeam = GameManager.getActiveTeam()

        val teamEmoji = findViewById<TextView>(R.id.tvCurrentTeamEmoji)
        val teamName = findViewById<TextView>(R.id.tvCurrentTeamName)
        val currentPlayer = findViewById<TextView>(R.id.tvCurrentPlayer)

        teamEmoji.text = currentTeam.emoji
        teamName.text = currentTeam.teamName
        currentPlayer.text = "${currentTeam.getRandomPlayer().playerName} speaks"
    }

    fun onClickConfirmationWindow(view: View) {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }
}