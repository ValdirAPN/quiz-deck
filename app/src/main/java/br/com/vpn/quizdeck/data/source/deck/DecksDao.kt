package br.com.vpn.quizdeck.data.source.deck

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.vpn.quizdeck.data.entity.DeckEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface DecksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(deck: DeckEntity)

    @Query("SELECT * FROM decks WHERE topicId=:id")
    fun getAllByTopicId(id: UUID): Flow<List<DeckEntity>>

    @Query("UPDATE decks SET title=:deckTitle WHERE id=:deckId")
    fun update(deckId: UUID, deckTitle: String)

    @Query("DELETE FROM decks WHERE id=:deckId")
    suspend fun delete(deckId: UUID): Int
}