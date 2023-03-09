package br.com.vpn.quizdeck.data.source.card

import br.com.vpn.quizdeck.data.ResultData
import br.com.vpn.quizdeck.data.repository.CardsRepository
import br.com.vpn.quizdeck.domain.model.Card
import kotlinx.coroutines.flow.Flow

class CardsRepositoryImpl(
    private val localDataSource: CardsDataSource
) : CardsRepository {
    override suspend fun save(card: Card) {
        localDataSource.save(card)
    }

    override fun getAllByDeckId(id: String): Flow<ResultData<List<Card>>> {
        return localDataSource.getAllByTopicId(id)
    }
}