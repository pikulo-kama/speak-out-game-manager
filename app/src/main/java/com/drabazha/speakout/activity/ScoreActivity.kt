package com.drabazha.speakout.activity

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TableRow.LayoutParams.MATCH_PARENT
import android.widget.TableRow.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.updatePadding
import com.drabazha.speakout.R
import com.drabazha.speakout.SpeakOutApplication
import com.drabazha.speakout.model.GameManager
import com.drabazha.speakout.model.SentenceHolder
import com.drabazha.speakout.model.Team
import com.drabazha.speakout.model.TeamHolder
import com.drabazha.speakout.service.SentenceGeneratorApi
import com.drabazha.speakout.util.SentenceApiConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScoreActivity : CustomCompatActivity() {

    @Inject
    lateinit var sentenceGeneratorApi: SentenceGeneratorApi

    private val coroutineContext = Dispatchers.IO

    private var job = Job()

    private var scope = CoroutineScope(coroutineContext + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        SpeakOutApplication.applicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        visualizeLeaderboard()

        scope.launch {
            withContext(Dispatchers.Main) {
                val response = sentenceGeneratorApi.getSentences(SentenceApiConstants.SENTENCE_POOL_SIZE)
                if (response.isSuccessful) {
                    val sentences = response.body()?.sentences
                    SentenceHolder.init(sentences!!)
                }
            }
        }
    }

    fun onClickScoreNext(view: View) {
        val intent = Intent(this, ConfirmationActivity::class.java)
        startActivity(intent)
    }

    private fun visualizeLeaderboard() {
        fillLeaderboard()

        GameManager.next()
        if (!GameManager.isGameInProgress() && GameManager.hasWinner()) {
            val intent = Intent(this, WinnerActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fillLeaderboard() {
        val leaderboard = findViewById<TableLayout>(R.id.tlLeaderboard)
        TeamHolder.teams.sortByDescending { team: Team -> team.teamScore }
        TeamHolder.teams.forEach { team: Team ->
            val tableRow = addTableRow()

            val teamNameView = TextView(this)
            teamNameView.text = "${team.emoji} ${team.teamName}"
            teamNameView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30f)

            val teamScoreView = TextView(this)
            team.summarizePoints()
            teamScoreView.text = "${team.teamScore} pts"
            teamScoreView.setTextColor(team.teamColor)
            teamScoreView.updatePadding(left = dpToPixels(R.dimen.dp_5))
            teamScoreView.setBackgroundResource(R.color.transparent_25)
            teamScoreView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30f)

            tableRow.addView(teamNameView)
            tableRow.addView(teamScoreView)
            leaderboard.addView(tableRow)
        }
    }

    private fun addTableRow(): TableRow {
        val tableRow = TableRow(this)
        tableRow.layoutParams = TableRow.LayoutParams(MATCH_PARENT, WRAP_CONTENT)

        tableRow.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.transparent_75))
        tableRow.updatePadding(left = dpToPixels(R.dimen.dp_5), top = dpToPixels(R.dimen.dp_5), bottom = dpToPixels(R.dimen.dp_5))
        return tableRow
    }
}