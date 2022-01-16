package com.drabazha.speakout.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.drabazha.speakout.R
import com.drabazha.speakout.SpeakOutApplication
import com.drabazha.speakout.adapter.TeamListAdapter
import com.drabazha.speakout.extension.showToastMessage
import com.drabazha.speakout.model.GameManager
import com.drabazha.speakout.model.Player
import com.drabazha.speakout.model.Team
import com.drabazha.speakout.model.TeamHolder
import com.drabazha.speakout.model.ToastMessageType.SUCCESS
import com.drabazha.speakout.service.ConfigService
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.ConfigKeys.MAX_PLAYERS_SIZE
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.ConfigKeys.MIN_TEAM_PLAYERS_SIZE
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.ConfigKeys.MIN_TEAM_SIZE
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.ConfigKeys.TARGET_GAME_POINTS
import com.drabazha.speakout.view.TeamListView
import javax.inject.Inject

class TeamsActivity : CustomCompatActivity() {

    @Inject lateinit var configService: ConfigService

    private lateinit var expandableListView: TeamListView

    private lateinit var addPlayerButton: Button
    private lateinit var addPlayerInput: EditText
    private lateinit var addTeamButton: Button
    private lateinit var addTeamInput: EditText
    private lateinit var formTeamButton: Button
    private lateinit var startGameButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        SpeakOutApplication.applicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams)

        expandableListView = findViewById(R.id.expLvTeamList)

        TeamHolder.minTeamSize = configService.getConfig(MIN_TEAM_SIZE)
        TeamHolder.minTeamPlayers = configService.getConfig(MIN_TEAM_PLAYERS_SIZE)
        TeamHolder.maxPlayerSize = configService.getConfig(MAX_PLAYERS_SIZE)
        updateAdapter()

        addTeamButton = findViewById(R.id.btnAddTeam)
        addPlayerButton = findViewById(R.id.btnAddPlayer)
        formTeamButton = findViewById(R.id.btnFormTeam)
        startGameButton = findViewById(R.id.btnStartGame)
        addPlayerInput = findViewById(R.id.etPlayerNameInput)
        addTeamInput = findViewById(R.id.etTeamNameInput)

        addTeamButton.isEnabled = false
        addPlayerButton.isEnabled = false
        startGameButton.isEnabled = false
        formTeamButton.isEnabled = false
        addPlayerInput.isEnabled = false

        if (TeamHolder.isMinimalTeamSizeReached()) {
            startGameButton.isEnabled = true
        }

        addOnTextChangedHandler(addPlayerInput, addPlayerButton)
        addOnTextChangedHandler(addTeamInput, addTeamButton)
    }

    fun onClickAddTeam(view: View) {
        TeamHolder.addTeam(Team(addTeamInput.text.toString(), ArrayList()))
        addTeamInput.text.clear()
        addTeamInput.isEnabled = false
        addTeamButton.isEnabled = false
        updateAdapter()

        addPlayerInput.isEnabled = true
    }

    fun onClickAddPlayer(view: View) {
        TeamHolder.addPlayer(Player(addPlayerInput.text.toString()))
        addPlayerInput.text.clear()
        updateAdapter()

        if (TeamHolder.isMaxPlayersSizeReached()) {
            addPlayerButton.isEnabled = false
            addPlayerInput.isEnabled = false
            Toast(this).showToastMessage("Max players limit reached", SUCCESS, this)
        }

        if (TeamHolder.isMinimalTeamPlayersSizeReached()) {
            formTeamButton.isEnabled = true
        }
    }

    fun onClickFormTeam(view: View) {
        formTeamButton.isEnabled = false
        addTeamInput.isEnabled = true
        addPlayerInput.isEnabled = false

        if (TeamHolder.isMinimalTeamSizeReached()) {
            startGameButton.isEnabled = true
            Toast(this).showToastMessage("Now you're able to start your game", SUCCESS, this)
        }
    }

    fun onClickStartGame(view: View) {
        val targetGamePoints = configService.getConfig(TARGET_GAME_POINTS)

        GameManager.setTeamHolder(TeamHolder)
        GameManager.setTargetGamePoints(targetGamePoints)
        val intent = Intent(this, ScoreActivity::class.java)
        startActivity(intent)
    }

    fun onClickOpenSettings(view: View) {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun updateAdapter() {
        expandableListView.setAdapter(TeamListAdapter(this, expandableListView, TeamHolder.teams))
    }

    private fun addOnTextChangedHandler(editText: EditText, button: Button) {
        editText.addTextChangedListener(object: TextWatcher {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                button.isEnabled = !editText.text.isBlank()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }
}