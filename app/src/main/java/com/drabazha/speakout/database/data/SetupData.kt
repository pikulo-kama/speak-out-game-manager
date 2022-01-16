package com.drabazha.speakout.database.data

import androidx.sqlite.db.SupportSQLiteDatabase
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.CONFIG_DESCRIPTION_COLUMN
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.CONFIG_KEY_COLUMN
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.CONFIG_TABLE_NAME
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.CONFIG_VALUE_COLUMN
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.ConfigKeys.MAX_PLAYERS_SIZE
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.ConfigKeys.MIN_TEAM_PLAYERS_SIZE
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.ConfigKeys.MIN_TEAM_SIZE
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.ConfigKeys.ROUND_TIME_SECONDS
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.ConfigKeys.TARGET_GAME_POINTS

class SetupData {
    companion object {

        fun setupConfigDefaultData(db: SupportSQLiteDatabase) {
            addConfigEntry(db, MAX_PLAYERS_SIZE, "Max Players", "6")
            addConfigEntry(db, MIN_TEAM_PLAYERS_SIZE, "Min players", "2")
            addConfigEntry(db, MIN_TEAM_SIZE, "Min teams", "2")
            addConfigEntry(db, ROUND_TIME_SECONDS, "Round time", "60")
            addConfigEntry(db, TARGET_GAME_POINTS, "Target points", "50")
        }

        private fun addConfigEntry(
            db: SupportSQLiteDatabase,
            configKey: String,
            configDescription: String,
            configValue: String
        ) {
            db.execSQL(
                "INSERT INTO ${CONFIG_TABLE_NAME}(${CONFIG_KEY_COLUMN}, ${CONFIG_DESCRIPTION_COLUMN}, ${CONFIG_VALUE_COLUMN}) " +
                        "VALUES ('${configKey}', '${configDescription}', '${configValue}')"
            )
        }
    }
}