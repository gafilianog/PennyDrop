package dev.gafilianog.pennydrop.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.gafilianog.pennydrop.game.AI
import dev.gafilianog.pennydrop.types.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [Game::class, Player::class, GameStatus::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class PennyDropDatabase: RoomDatabase() {
    abstract fun pennyDropDao(): PennyDropDao

    companion object {
        @Volatile
        private var instance: PennyDropDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): PennyDropDatabase =
            this.instance?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    PennyDropDatabase::class.java,
                    "PennyDropDatabase"
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        scope.launch {
                            instance?.pennyDropDao()?.insertPlayers(
                                AI.basicAI.map(AI::toPlayer)
                            )
                        }
                    }
                }).build()
                this.instance = instance
                instance
            }
    }
}