package br.com.vpn.quizdeck.data.source.deck

import br.com.vpn.quizdeck.data.ResultData
import br.com.vpn.quizdeck.data.repository.DecksRepository
import br.com.vpn.quizdeck.domain.model.Deck
import br.com.vpn.quizdeck.domain.model.Topic
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow

class DecksRepositoryImpl(
    private val localDataSource: DecksDataSource
) : DecksRepository {
    override suspend fun save(deck: Deck) {
        localDataSource.save(deck)
    }

    override fun getAllByTopicId(id: String): Flow<ResultData<List<Deck>>> {
        return localDataSource.getAllByTopicId(id)
    }

    override suspend fun update(deck: Deck) {
        coroutineScope {
            localDataSource.update(deck)
        }
    }

    override suspend fun delete(id: String) {
        coroutineScope {
            localDataSource.delete(id)
        }
    }
}