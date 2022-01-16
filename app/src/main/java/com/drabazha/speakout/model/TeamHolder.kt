package com.drabazha.speakout.model

object TeamHolder {

    var teams: ArrayList<Team> = ArrayList()

    private var currentTeamId: Int = -1

    var maxPlayerSize: Int = 0
    var minTeamPlayers: Int = 0
    var minTeamSize: Int = 0

    fun addTeam(team: Team) {
        teams.add(team)
        currentTeamId++
    }

    fun addPlayer(player: Player) {
        teams[currentTeamId].players.add(player)
    }

    fun isMaxPlayersSizeReached(): Boolean {
        val playersSize = teams.sumOf { team -> team.players.size }
        return playersSize >= maxPlayerSize
    }

    fun isMinimalTeamPlayersSizeReached(): Boolean {
        return teams[currentTeamId].players.size >= minTeamPlayers
    }

    fun isMinimalTeamSizeReached(): Boolean {
        return teams.size >= minTeamSize
    }
}