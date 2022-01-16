package com.drabazha.speakout.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.CONFIG_DESCRIPTION_COLUMN
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.CONFIG_KEY_COLUMN
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.CONFIG_TABLE_NAME
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.CONFIG_VALUE_COLUMN

@Entity(tableName = CONFIG_TABLE_NAME)
data class Config(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = CONFIG_KEY_COLUMN) val configKey: String?,
    @ColumnInfo(name = CONFIG_VALUE_COLUMN) val configValue: String?,
    @ColumnInfo(name = CONFIG_DESCRIPTION_COLUMN) val configDescription: String?
)