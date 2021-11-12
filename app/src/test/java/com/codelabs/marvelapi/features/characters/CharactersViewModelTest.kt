package com.codelabs.marvelapi.features.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.codelabs.marvelapi.features.characters.data.FakeCharacterRepository

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharactersViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CharactersViewModel

    @Before
    fun setUp() {
        viewModel = CharactersViewModel(FakeCharacterRepository())
    }

    @Test
    fun `get first page characters`() {
        TODO("Not yet implemented")
    }
}