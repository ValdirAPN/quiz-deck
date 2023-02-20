package br.com.vpn.quizdeck.di

import android.content.Context
import androidx.room.Room
import br.com.vpn.quizdeck.data.repository.TopicsRepository
import br.com.vpn.quizdeck.data.source.QuizDeckDatabase
import br.com.vpn.quizdeck.data.source.topic.TopicsDataSource
import br.com.vpn.quizdeck.data.source.topic.TopicsLocalDataSource
import br.com.vpn.quizdeck.data.source.topic.TopicsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModules {

    @Singleton
    @Provides
    fun provideTopicsRepository(
        localDataSource: TopicsDataSource
    ): TopicsRepository {
        return TopicsRepositoryImpl(localDataSource)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModules {

    @Singleton
    @Provides
    fun provideTopicsLocalDataSource(database: QuizDeckDatabase): TopicsDataSource {
        return TopicsLocalDataSource(database.topicsDao())
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : QuizDeckDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            QuizDeckDatabase::class.java,
            "QuizDeck.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}