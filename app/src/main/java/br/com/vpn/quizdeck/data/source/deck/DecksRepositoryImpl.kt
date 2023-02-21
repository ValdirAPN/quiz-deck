package br.com.vpn.quizdeck.data.source.deck

import br.com.vpn.quizdeck.data.ResultData
import br.com.vpn.quizdeck.data.repository.DecksRepository
import br.com.vpn.quizdeck.domain.model.Deck
import kotlinx.coroutines.flow.Flow

class DecksRepositoryImpl(
    private val localDataSource: DecksDataSource
) : DecksRepository {
    override suspend fun save(deck: Deck) {
        localDataSource.save(deck)
    }

    override fun getAllByTopicId(id: String): Flow<ResultData<List<Deck>>> {
        return localDataSource.getByTopicId(id)
    }
}