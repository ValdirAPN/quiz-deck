package br.com.vpn.quizdeck.presentation.ui.topic.usecases

import br.com.vpn.quizdeck.data.repository.DecksRepository
import javax.inject.Inject

class DeleteDeckUseCase @Inject constructor(
    private val decksRepository: DecksRepository
) {

    suspend operator fun invoke(id: String) {
        decksRepository.delete(id)
    }
}