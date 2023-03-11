package br.com.vpn.quizdeck.data.repository

import br.com.vpn.quizdeck.data.ResultData
import br.com.vpn.quizdeck.domain.model.Card
import kotlinx.coroutines.flow.Flow

interface CardsRepository {
    suspend fun save(card: Card)
    fun getAllByDeckId(id: String): Flow<ResultData<List<Card>>>
    suspend fun update(card: Card)
    suspend fun delete(id: String)
}