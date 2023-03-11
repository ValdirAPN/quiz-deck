package br.com.vpn.quizdeck.presentation.ui.deck.usecases

import br.com.vpn.quizdeck.data.repository.CardsRepository
import javax.inject.Inject

class DeleteCardUseCase @Inject constructor(
    private val cardsRepository: CardsRepository
) {

    suspend operator fun invoke(id: String) {
        cardsRepository.delete(id)
    }
}