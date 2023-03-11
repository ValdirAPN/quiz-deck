package br.com.vpn.quizdeck.data.source.topic

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.vpn.quizdeck.data.entity.TopicEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface TopicsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(topic: TopicEntity)

    @Query("SELECT * FROM topics")
    fun observe(): Flow<List<TopicEntity>>

    @Query("SELECT * FROM topics WHERE id=:id")
    fun getById(id: UUID): Flow<TopicEntity>

    @Query("UPDATE topics SET title=:topicTitle WHERE id=:topicId")
    fun update(topicId: UUID, topicTitle: String)

    @Query("DELETE FROM topics WHERE id=:topicId")
    suspend fun deleteTopicById(topicId: UUID): Int
}