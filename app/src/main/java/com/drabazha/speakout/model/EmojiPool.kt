package com.drabazha.speakout.model

class EmojiPool {

    companion object {
        private var usedEmojis: ArrayList<String> = ArrayList()

        private var emojiPool: ArrayList<String> = arrayListOf(
            "\uD83E\uDD15",
            "\uD83E\uDD2A",
            "\uD83E\uDD22",
            "\uD83E\uDD20",
            "\uD83E\uDD13",
            "\uD83E\uDD2C",
            "\uD83D\uDE08",
            "\uD83D\uDCA9",
            "\uD83D\uDC7D"
        )

        fun getRandomEmoji(): String {
            var emoji: String = ""

            while (emoji.isBlank() || usedEmojis.contains(emoji)) {
                emoji = emojiPool.random()
            }
            usedEmojis.add(emoji)
            return emoji
        }
    }
}