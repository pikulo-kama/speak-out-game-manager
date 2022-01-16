package com.drabazha.speakout.activity

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TableRow.LayoutParams.MATCH_PARENT
import android.widget.TableRow.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.updatePadding
import com.drabazha.speakout.R
import com.drabazha.speakout.SpeakOutApplication
import com.drabazha.speakout.entity.Config
import com.drabazha.speakout.extension.showToastMessage
import com.drabazha.speakout.model.ToastMessageType
import com.drabazha.speakout.model.ToastMessageType.*
import com.drabazha.speakout.service.ConfigService
import javax.inject.Inject

class SettingsActivity : CustomCompatActivity() {

    @Inject
    lateinit var configService: ConfigService

    override fun onCreate(savedInstanceState: Bundle?) {
        SpeakOutApplication.applicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        visualizeSettings()
    }

    private fun visualizeSettings() {
        val settings = findViewById<TableLayout>(R.id.tlSettings)

        val dbSettings = configService.getAll()

        dbSettings.forEach { config: Config ->
            val tableRow = TableRow(this)
            tableRow.layoutParams = TableRow.LayoutParams(MATCH_PARENT, WRAP_CONTENT)

            val descriptionView = TextView(this)
            descriptionView.text = config.configDescription
            descriptionView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25f)
            descriptionView.updatePadding(right = dpToPixels(R.dimen.dp_10))

            val valueView = EditText(this)
            valueView.setText(config.configValue)
            valueView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25f)
            valueView.inputType = InputType.TYPE_CLASS_NUMBER
            valueView.gravity = Gravity.CENTER

            val keyView = TextView(this)
            keyView.text = config.configKey
            keyView.visibility = View.GONE

            tableRow.addView(descriptionView)
            tableRow.addView(valueView)
            tableRow.addView(keyView)

            settings.addView(tableRow)
        }
    }

    fun onClickSaveSettings(view: View) {
        findViewById<TableLayout>(R.id.tlSettings).children.forEach { tableRow: View ->
            val value = (tableRow as TableRow).getChildAt(1) as EditText
            val key = tableRow.getChildAt(2) as TextView

            configService.updateConfiguration(key.text.toString(), value.text.toString())
        }
        Toast(this).showToastMessage("Configuration parameters updated", SUCCESS, this)
        val intent = Intent(this, TeamsActivity::class.java)
        startActivity(intent)
    }
}