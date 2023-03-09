package br.com.vpn.quizdeck.data.source.card

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.vpn.quizdeck.data.entity.CardEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface CardsDao {

    @Insert
    suspend fun insert(card: CardEntity)

    @Query("SELECT * FROM cards WHERE deckId=:id")
    fun getAllByDeckId(id: UUID): Flow<List<CardEntity>>
}