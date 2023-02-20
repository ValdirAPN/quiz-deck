package br.com.vpn.quizdeck.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.vpn.quizdeck.data.dao.TopicsDao
import br.com.vpn.quizdeck.data.entity.TopicEntity

@Database(entities = [
    TopicEntity::class
], version = 1, exportSchema = false)
abstract class QuizDeckDatabase : RoomDatabase() {

    abstract fun topicsDao(): TopicsDao
}