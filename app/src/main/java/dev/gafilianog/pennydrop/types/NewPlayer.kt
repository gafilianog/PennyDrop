package dev.gafilianog.pennydrop.types

import androidx.databinding.ObservableBoolean

data class NewPlayer(
    val playerName: String = "",
    val isHuman: ObservableBoolean = ObservableBoolean(true),
    val canBeRemoved: Boolean = true,
    val canBeToggled: Boolean = true,
    var isIncluded: ObservableBoolean = ObservableBoolean(!canBeRemoved)
)