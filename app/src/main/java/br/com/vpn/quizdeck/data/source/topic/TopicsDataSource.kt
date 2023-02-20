package br.com.vpn.quizdeck.data.source.topic

import br.com.vpn.quizdeck.data.ResultData
import br.com.vpn.quizdeck.domain.model.Topic
import kotlinx.coroutines.flow.Flow

interface TopicsDataSource {
    suspend fun saveTopic(topic: Topic)
    fun getTopicsStream(): Flow<ResultData<List<Topic>>>
}