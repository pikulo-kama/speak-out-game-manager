package com.drabazha.speakout.extension

import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.drabazha.speakout.R
import com.drabazha.speakout.model.ToastMessageType

fun Toast.showToastMessage(message: String, messageType: ToastMessageType, activity: Activity) {
    val layout = activity.layoutInflater.inflate (
        R.layout.layout_custom_toast,
        activity.findViewById(R.id.rlToastContainer)
    )

    val toastBody = layout.findViewById<RelativeLayout>(R.id.rlToastBody)
    toastBody.setBackgroundColor(Color.parseColor(messageType.backgroundColor))

    val toastBorder = layout.findViewById<FrameLayout>(R.id.flToastBorder)
    toastBorder.setBackgroundColor(Color.parseColor(messageType.borderColor))

    val textView = layout.findViewById<TextView>(R.id.tvToastMessage)
    textView.text = message

    this.apply {
        setGravity(Gravity.TOP, 0, 10)
        duration = Toast.LENGTH_SHORT
        view = layout
        show()
    }
}