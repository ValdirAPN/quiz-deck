package br.com.vpn.quizdeck.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.vpn.quizdeck.data.entity.CardEntity
import br.com.vpn.quizdeck.data.source.deck.DecksDao
import br.com.vpn.quizdeck.data.source.topic.TopicsDao
import br.com.vpn.quizdeck.data.entity.DeckEntity
import br.com.vpn.quizdeck.data.entity.TopicEntity
import br.com.vpn.quizdeck.data.source.card.CardsDao

@Database(entities = [
    TopicEntity::class,
    DeckEntity::class,
    CardEntity::class,
], version = 1, exportSchema = false)
abstract class QuizDeckDatabase : RoomDatabase() {

    abstract fun topicsDao(): TopicsDao
    abstract fun decksDao(): DecksDao
    abstract fun cardsDao(): CardsDao
}