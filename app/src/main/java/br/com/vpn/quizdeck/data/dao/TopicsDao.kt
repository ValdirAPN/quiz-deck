package br.com.vpn.quizdeck.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.vpn.quizdeck.data.entity.TopicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicsDao {

    @Insert
    suspend fun insert(topic: TopicEntity)

    @Query("SELECT * FROM topic")
    fun getAll(): List<TopicEntity>

    @Query("SELECT * FROM topic")
    fun observe(): Flow<List<TopicEntity>>
}