package br.com.vpn.quizdeck.data.source.topic

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.vpn.quizdeck.data.entity.TopicEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface TopicsDao {

    @Insert
    suspend fun insert(topic: TopicEntity)

    @Query("SELECT * FROM topics")
    fun observe(): Flow<List<TopicEntity>>

    @Query("SELECT * FROM topics WHERE id=:id")
    fun getById(id: UUID): Flow<TopicEntity>
}