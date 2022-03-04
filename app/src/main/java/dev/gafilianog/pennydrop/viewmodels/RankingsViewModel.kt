package dev.gafilianog.pennydrop.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import dev.gafilianog.pennydrop.data.PennyDropDatabase
import dev.gafilianog.pennydrop.data.PennyDropRepository
import dev.gafilianog.pennydrop.types.PlayerSummary

class RankingsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PennyDropRepository

    val playerSummaries: LiveData<List<PlayerSummary>>

    init {
        this.repository = PennyDropDatabase
            .getDatabase(application, viewModelScope)
            .pennyDropDao()
            .let { dao ->
                PennyDropRepository.getInstance(dao)
            }

        playerSummaries =
            Transformations.map(
                this.repository.getCompletedGameStatusesWithPlayers()
            ) { statusesWithPlayers ->
                statusesWithPlayers
                    .groupBy { it.player }
                    .map { (player, statuses) ->
                        PlayerSummary(
                            player.playerId,
                            player.playerName,
                            statuses.count(),
                            statuses.count { it.gameStatus.pennies == 0 },
                            player.isHuman
                        )
                    }
                    .sortedWith(compareBy({ -it.wins }, { -it.gamesPlayed }))
            }
    }
}