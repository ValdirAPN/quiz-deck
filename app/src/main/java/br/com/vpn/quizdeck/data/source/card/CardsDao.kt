package br.com.vpn.quizdeck.data.source.card

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.vpn.quizdeck.data.entity.CardEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface CardsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(card: CardEntity)

    @Query("SELECT * FROM cards WHERE deckId=:id")
    fun getAllByDeckId(id: UUID): Flow<List<CardEntity>>

    @Query("UPDATE cards SET statement=:cardStatement, answer=:cardAnswer WHERE id=:cardId")
    fun update(cardId: UUID, cardStatement: String, cardAnswer: String)

    @Query("DELETE FROM cards WHERE id=:cardId")
    suspend fun delete(cardId: UUID): Int
}