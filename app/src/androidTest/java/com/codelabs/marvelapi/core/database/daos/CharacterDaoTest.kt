package com.codelabs.marvelapi.core.database.daos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.codelabs.marvelapi.core.database.MarvelDatabase
import com.codelabs.marvelapi.core.database.entities.CharacterEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class CharacterDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MarvelDatabase
    private lateinit var dao: CharacterDao

    private val entities = listOf(
        CharacterEntity(
            id = 1,
            name = "Spider-Man",
            description = "",
            thumbnail = null,
        ),
        CharacterEntity(
            id = 2,
            name = "Hulk",
            description = "",
            thumbnail = null,
        ),
        CharacterEntity(
            id = 3,
            name = "Iron-Man",
            description = "",
            thumbnail = null,
        )
    )

    @Before
    fun setUp() {
        database = Room
            .inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                MarvelDatabase::class.java
            )
            .allowMainThreadQueries()
            .build()

        dao = database.characterDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertCharacter() = runBlockingTest {
        dao.insert(entities.first())

        val result = dao.all()

        assertThat(result).contains(entities.first())
    }

    @Test
    fun findCharactersStartingByNameQuery() = runBlockingTest {
        dao.insertAll(entities)

        val result = dao.where(2, 0, "spider")

        assertThat(result).contains(entities.first())
    }

    @Test
    fun findCharacterById() = runBlockingTest {
        dao.insertAll(entities)

        val result = dao.findCharacterById(2)

        assertThat(result?.id).isEqualTo(2)
    }

}