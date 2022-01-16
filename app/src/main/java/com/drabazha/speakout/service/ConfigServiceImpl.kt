package com.drabazha.speakout.service

import com.drabazha.speakout.database.AppDatabase
import com.drabazha.speakout.entity.Config
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigServiceImpl @Inject constructor(private var db: AppDatabase) : ConfigService {

    override fun getConfig(key: String): Int {
        return db.configDao().findByKey(key).configValue!!.toInt()
    }

    override fun updateConfiguration(configKey: String, configValue: String) {
        db.configDao().update(configKey, configValue)
    }

    override fun getAll(): List<Config> {
        return db.configDao().findAll()
    }
}