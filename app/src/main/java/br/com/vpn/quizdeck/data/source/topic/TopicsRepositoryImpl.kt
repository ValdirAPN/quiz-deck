package br.com.vpn.quizdeck.data.source.topic

import br.com.vpn.quizdeck.data.ResultData
import br.com.vpn.quizdeck.data.repository.TopicsRepository
import br.com.vpn.quizdeck.domain.model.Topic
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class TopicsRepositoryImpl(
    private val localDataSource: TopicsDataSource
) : TopicsRepository {
    override suspend fun saveTopic(topic: Topic) {
        localDataSource.saveTopic(topic)
    }

    override fun getTopicsStream(): Flow<ResultData<List<Topic>>> {
        return localDataSource.getTopicsStream()
    }

    override fun getById(id: String): Flow<ResultData<Topic>> {
        return localDataSource.getById(id)
    }
}