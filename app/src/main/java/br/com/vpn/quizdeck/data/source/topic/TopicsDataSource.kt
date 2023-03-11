package br.com.vpn.quizdeck.data.source.topic

import br.com.vpn.quizdeck.data.ResultData
import br.com.vpn.quizdeck.domain.model.Topic
import kotlinx.coroutines.flow.Flow
import java.util.*

interface TopicsDataSource {
    suspend fun saveTopic(topic: Topic)
    fun getTopicsStream(): Flow<ResultData<List<Topic>>>
    fun getById(id: String): Flow<ResultData<Topic>>

    suspend fun update(topic: Topic)

    suspend fun deleteTopic(topicId: String)
}