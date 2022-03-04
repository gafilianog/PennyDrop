package dev.gafilianog.pennydrop.data

import androidx.room.Embedded
import androidx.room.Relation
import dev.gafilianog.pennydrop.types.Player

data class GameStatusWithPlayer(
    @Embedded val gameStatus: GameStatus,
    @Relation(
        parentColumn = "playerId",
        entityColumn = "playerId"
    )
    val player: Player
)
