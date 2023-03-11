package br.com.vpn.quizdeck.presentation.ui.home.usecases

import br.com.vpn.quizdeck.data.repository.TopicsRepository
import br.com.vpn.quizdeck.domain.model.Topic
import javax.inject.Inject

class DeleteTopicUseCase @Inject constructor(
    private val topicsRepository: TopicsRepository
) {

    suspend operator fun invoke(topicId: String) {
        topicsRepository.deleteTopic(topicId)
    }
}