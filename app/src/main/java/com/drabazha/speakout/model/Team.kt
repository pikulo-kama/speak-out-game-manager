package com.drabazha.speakout.model

import android.graphics.Color
import java.util.*
import java.util.Objects.isNull
import java.util.stream.Collectors
import kotlin.collections.ArrayList
import kotlin.random.Random

class Team(teamName: String, players: ArrayList<Player>) {

    var teamScore: Int = 0

    var roundScore: Int = 0

    var teamColor: Int =
        Color.argb(65,
            Random.nextInt(0, 255),
            Random.nextInt(0, 255),
            Random.nextInt(0, 255))

    var emoji: String = EmojiPool.getRandomEmoji()

    var teamName: String = teamName
        private set

    var players: ArrayList<Player> = players
        private set

    private var playersInactive: ArrayList<Player> = ArrayList()

    fun addScorePoint() {
        ++roundScore
    }

    fun summarizePoints() {
        teamScore += roundScore
        roundScore = 0
    }

    fun getRandomPlayer(): Player {
        var player: Player? = null

        while (isNull(player) || playersInactive.contains(player)) {
            player = players.random()
        }
        playersInactive.add(player!!)
        if (playersInactive.size == players.size) {
            playersInactive = ArrayList()
        }
        return player
    }
}