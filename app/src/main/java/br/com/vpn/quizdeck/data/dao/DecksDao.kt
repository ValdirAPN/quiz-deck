package br.com.vpn.quizdeck.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.vpn.quizdeck.data.entity.DeckEntity
import br.com.vpn.quizdeck.data.entity.TopicEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface DecksDao {

    @Insert
    suspend fun insert(deck: DeckEntity)

    @Query("SELECT * FROM decks WHERE topicId=:id")
    fun getByTopicId(id: UUID): Flow<List<DeckEntity>>
}