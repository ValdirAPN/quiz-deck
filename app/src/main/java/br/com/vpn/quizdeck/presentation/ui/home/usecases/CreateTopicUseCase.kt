package br.com.vpn.quizdeck.presentation.ui.home.usecases

import br.com.vpn.quizdeck.data.repository.TopicsRepository
import br.com.vpn.quizdeck.domain.model.Topic
import javax.inject.Inject

class CreateTopicUseCase @Inject constructor(
    private val topicsRepository: TopicsRepository
) {

    suspend operator fun invoke(topic: Topic) {
        topicsRepository.saveTopic(topic)
    }
}