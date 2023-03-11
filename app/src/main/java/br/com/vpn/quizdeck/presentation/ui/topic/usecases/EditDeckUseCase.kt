package br.com.vpn.quizdeck.presentation.ui.topic.usecases

import br.com.vpn.quizdeck.data.repository.DecksRepository
import br.com.vpn.quizdeck.domain.model.Deck
import javax.inject.Inject

class EditDeckUseCase @Inject constructor(
    private val decksRepository: DecksRepository
) {

    suspend operator fun invoke(deck: Deck) {
        decksRepository.update(deck)
    }
}