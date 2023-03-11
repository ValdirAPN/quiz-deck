package br.com.vpn.quizdeck.di

import android.content.Context
import androidx.room.Room
import br.com.vpn.quizdeck.data.repository.CardsRepository
import br.com.vpn.quizdeck.data.repository.DecksRepository
import br.com.vpn.quizdeck.data.repository.TopicsRepository
import br.com.vpn.quizdeck.data.source.QuizDeckDatabase
import br.com.vpn.quizdeck.data.source.card.CardsDataSource
import br.com.vpn.quizdeck.data.source.card.CardsLocalDataSource
import br.com.vpn.quizdeck.data.source.card.CardsRepositoryImpl
import br.com.vpn.quizdeck.data.source.deck.DecksDataSource
import br.com.vpn.quizdeck.data.source.deck.DecksLocalDataSource
import br.com.vpn.quizdeck.data.source.deck.DecksRepositoryImpl
import br.com.vpn.quizdeck.data.source.topic.TopicsDataSource
import br.com.vpn.quizdeck.data.source.topic.TopicsLocalDataSource
import br.com.vpn.quizdeck.data.source.topic.TopicsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
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

    @Singleton
    @Provides
    fun provideDecksRepository(
        localDataSource: DecksDataSource
    ): DecksRepository {
        return DecksRepositoryImpl(localDataSource)
    }

    @Singleton
    @Provides
    fun provideCardsRepository(
        localDataSource: CardsDataSource
    ): CardsRepository {
        return CardsRepositoryImpl(localDataSource)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModules {

    @Singleton
    @Provides
    fun provideTopicsLocalDataSource(
        database: QuizDeckDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): TopicsDataSource {
        return TopicsLocalDataSource(database.topicsDao(), ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideDecksLocalDataSource(
        database: QuizDeckDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): DecksDataSource {
        return DecksLocalDataSource(database.decksDao())
    }

    @Singleton
    @Provides
    fun provideCardsLocalDataSource(
        database: QuizDeckDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): CardsDataSource {
        return CardsLocalDataSource(database.cardsDao())
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