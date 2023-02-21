package br.com.vpn.quizdeck.presentation.ui.topic.usecases

import br.com.vpn.quizdeck.data.repository.DecksRepository
import javax.inject.Inject

class LoadDecksUseCase @Inject constructor(
    private val decksRepository: DecksRepository
) {

    operator fun invoke(topicId: String) = decksRepository.getAllByTopicId(topicId)
}