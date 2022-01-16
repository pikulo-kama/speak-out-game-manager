package com.drabazha.speakout.model

object GameManager {

    private var teamHolder: TeamHolder? = null
    private var currentTeamId: Int = -1
    private var targetGamePoints: Int = 0


    fun setTeamHolder(teamHolder: TeamHolder) {
        this.teamHolder = teamHolder
        currentTeamId = -1
    }

    fun next(): GameManager {
        if (currentTeamId >= teamHolder!!.teams.size - 1) {
            currentTeamId = -1
        }
        ++currentTeamId
        return this
    }

    fun setTargetGamePoints(target: Int) {
        targetGamePoints = target
    }

    fun getActiveTeam(): Team = teamHolder!!.teams[currentTeamId]

    fun isGameInProgress(): Boolean = currentTeamId > 0

    fun hasWinner(): Boolean {
        return getWinner().teamScore >= targetGamePoints
    }

    fun getWinner(): Team = TeamHolder.teams.maxByOrNull { team: Team -> team.teamScore }!!

    fun reset() {
        teamHolder!!.teams.forEach { team: Team -> team.teamScore = 0 }
        currentTeamId = -1
    }
}