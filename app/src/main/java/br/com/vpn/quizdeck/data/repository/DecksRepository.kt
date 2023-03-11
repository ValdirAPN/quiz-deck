package br.com.vpn.quizdeck.data.repository

import br.com.vpn.quizdeck.data.ResultData
import br.com.vpn.quizdeck.domain.model.Deck
import kotlinx.coroutines.flow.Flow

interface DecksRepository {
    suspend fun save(deck: Deck)
    fun getAllByTopicId(id: String): Flow<ResultData<List<Deck>>>
    suspend fun update(deck: Deck)
    suspend fun delete(id: String)
}