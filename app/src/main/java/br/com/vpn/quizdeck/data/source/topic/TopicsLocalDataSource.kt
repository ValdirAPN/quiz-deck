package br.com.vpn.quizdeck.data.source.topic

import br.com.vpn.quizdeck.data.ResultData
import br.com.vpn.quizdeck.domain.model.Topic
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.*

class TopicsLocalDataSource(
    private val dao: TopicsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
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

    override suspend fun update(topic: Topic) = withContext(ioDispatcher) {
        dao.update(topic.id, topic.title)
    }

    override suspend fun deleteTopic(topicId: String) = withContext<Unit>(ioDispatcher) {
        dao.deleteTopicById(UUID.fromString(topicId))
    }
}