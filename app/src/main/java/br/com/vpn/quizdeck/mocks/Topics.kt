package br.com.vpn.quizdeck.mocks

import br.com.vpn.quizdeck.domain.model.Topic
import java.util.*

fun topics(): List<Topic> {
    return listOf(
        Topic(UUID.randomUUID(), "História"),
        Topic(UUID.randomUUID(), "Matemática"),
        Topic(UUID.randomUUID(), "Inglês"),
    )
}