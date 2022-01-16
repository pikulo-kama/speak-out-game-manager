package com.drabazha.speakout.service

import com.drabazha.speakout.entity.Config

interface ConfigService {

    fun getConfig(key: String): Int

    fun updateConfiguration(configKey: String, configValue: String)

    fun getAll(): List<Config>
}