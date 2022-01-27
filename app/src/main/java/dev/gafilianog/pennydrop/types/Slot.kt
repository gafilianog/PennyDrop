package dev.gafilianog.pennydrop.types

data class Slot(
    val number: Int,
    val canBeFilled: Boolean = true,
    var isFilled: Boolean = false,
    var lastRolled: Boolean = false
)

fun List<Slot>.clear() = this.forEach { slot ->
    slot.isFilled = false
    slot.lastRolled = false
}