package dev.gafilianog.pennydrop.types

import androidx.databinding.ObservableBoolean
import dev.gafilianog.pennydrop.game.AI

data class NewPlayer(
    var playerName: String = "",
    var isIncluded: ObservableBoolean = ObservableBoolean(false),
    val isHuman: ObservableBoolean = ObservableBoolean(true),
    val canBeRemoved: Boolean = true,
    val canBeToggled: Boolean = true,
    var selectedAIPosition: Int = -1
) {
    fun selectedAI() = if (!isHuman.get()) {
        AI.basicAI.getOrNull(selectedAIPosition)
    } else {
        null
    }
}