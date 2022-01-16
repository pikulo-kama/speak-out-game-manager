package com.drabazha.speakout.util

class TimerUtil {
    companion object {
        fun formatSeconds(pSeconds: Int): String {
            val minutes = pSeconds / 60
            val seconds = (pSeconds % 60).toString().padStart(2, '0')
            return "${minutes}:${seconds}"
        }

        fun decreaseBy(time:String, seconds: Int): String {
            return formatSeconds(parseFromString(time) - seconds)
        }

        private fun parseFromString(time: String): Int {
            val timeSplit = time.split(":")
            return (timeSplit[0].toInt() * 60) + timeSplit[1].toInt()
        }
    }
}