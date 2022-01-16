package com.drabazha.speakout.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.drabazha.speakout.dao.ConfigDao
import com.drabazha.speakout.database.data.SetupData
import com.drabazha.speakout.entity.Config
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.CONFIG_DESCRIPTION_COLUMN
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.CONFIG_KEY_COLUMN
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.CONFIG_TABLE_NAME
import com.drabazha.speakout.util.DatabaseConstants.ConfigTableConstants.CONFIG_VALUE_COLUMN
import com.drabazha.speakout.util.DatabaseConstants.DATABASE_NAME
import com.drabazha.speakout.util.DatabaseConstants.DATABASE_VERSION

@Database(entities = [Config::class], version = DATABASE_VERSION, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun configDao(): ConfigDao

    companion object {

        @Volatile private var instance: AppDatabase? = null

        private val MIGRATION_ADD_CONFIG_TABLE : Migration = object : Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {

                database.execSQL("CREATE TABLE IF NOT EXISTS `$CONFIG_TABLE_NAME`\n" +
                        "(\n" +
                        "    `uid`          INTEGER NOT NULL,\n" +
                        "    `$CONFIG_KEY_COLUMN`   TEXT,\n" +
                        "    `$CONFIG_VALUE_COLUMN` TEXT,\n" +
                        "    `$CONFIG_DESCRIPTION_COLUMN` TEXT,\n" +
                        "    PRIMARY KEY (`uid`)\n" +
                        ");")
            }
        }

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addMigrations(MIGRATION_ADD_CONFIG_TABLE)
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        SetupData.setupConfigDefaultData(db)
                    }
                })
                .allowMainThreadQueries()
                .build()
        }
    }
}