package br.com.vpn.quizdeck.data.source.topic

import br.com.vpn.quizdeck.data.ResultData
import br.com.vpn.quizdeck.data.dao.TopicsDao
import br.com.vpn.quizdeck.domain.model.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class TopicsLocalDataSource(
    private val dao: TopicsDao
) : TopicsDataSource {
    override suspend fun saveTopic(topic: Topic) {
        dao.insert(topic.toEntity())
    }

    override fun getTopicsStream(): Flow<ResultData<List<Topic>>> {
        return dao.observe().map { topics ->
            ResultData.Success(
                topics.map { topic ->
                    Topic.fromEntity(topic)
                }
            )
        }
    }

    override fun getById(id: String): Flow<ResultData<Topic>> {
        return dao.getById(UUID.fromString(id)).map { topic ->
            ResultData.Success(
                Topic.fromEntity(topic)
            )
        }
    }
}