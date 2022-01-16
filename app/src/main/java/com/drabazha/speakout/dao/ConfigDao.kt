package com.drabazha.speakout.dao

import androidx.room.Dao
import androidx.room.Query
import com.drabazha.speakout.entity.Config
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.CONFIG_DESCRIPTION_COLUMN
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.CONFIG_KEY_COLUMN
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.CONFIG_TABLE_NAME
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.CONFIG_VALUE_COLUMN

@Dao
interface ConfigDao {

    @Query("SELECT * FROM $CONFIG_TABLE_NAME")
    fun findAll(): List<Config>

    @Query("SELECT * FROM $CONFIG_TABLE_NAME WHERE $CONFIG_KEY_COLUMN = :key")
    fun findByKey(key: String): Config

    @Query("UPDATE $CONFIG_TABLE_NAME " +
            "SET $CONFIG_VALUE_COLUMN = :value " +
            "WHERE $CONFIG_KEY_COLUMN = :key")
    fun update(key: String, value: String)

    @Query("INSERT INTO $CONFIG_TABLE_NAME ($CONFIG_KEY_COLUMN, $CONFIG_VALUE_COLUMN, $CONFIG_DESCRIPTION_COLUMN) " +
            "VALUES (:key, :value, :description)")
    fun insert(key: String, value: String, description: String)
}