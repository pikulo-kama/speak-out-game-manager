package com.drabazha.speakout.util


object SentenceApiConstants {

    const val SENTENCE_BUILDER_API_BASE_URL = "https://sentence-builder-api.herokuapp.com/"

    const val SENTENCE_POOL_SIZE = 60

    const val MIN_SENTENCE_POOL_SIZE = 10
}

object DatabaseConstants {

    const val DATABASE_VERSION: Int = 2

    const val DATABASE_NAME: String = "local.sqlite"

    object ConfigTableConstants {

        const val CONFIG_TABLE_NAME: String = "config"

        const val CONFIG_KEY_COLUMN = "config_key"

        const val CONFIG_VALUE_COLUMN = "config_value"

        const val CONFIG_DESCRIPTION_COLUMN = "config_description"

        object ConfigKeys {

            const val MIN_TEAM_SIZE = "MIN_TEAM_SIZE"

            const val MIN_TEAM_PLAYERS_SIZE = "MIN_TEAM_PLAYERS_SIZE"

            const val MAX_PLAYERS_SIZE = "MAX_PLAYERS_SIZE"

            const val ROUND_TIME_SECONDS = "ROUND_TIME_SECONDS"

            const val TARGET_GAME_POINTS = "TARGET_GAME_POINTS"
        }
    }
}
