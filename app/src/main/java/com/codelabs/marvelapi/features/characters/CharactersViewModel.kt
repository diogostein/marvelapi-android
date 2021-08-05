package com.codelabs.marvelapi.features.characters

import androidx.lifecycle.*
import com.codelabs.marvelapi.core.RequestState
import com.codelabs.marvelapi.features.characters.data.CharacterRepository
import kotlinx.coroutines.launch

class CharactersViewModel(private val repository: CharacterRepository) : ViewModel() {
    private val _state = MutableLiveData<RequestState>()

    val state: LiveData<RequestState> = _state

    fun getCharacters() {
        viewModelScope.launch {
            _state.value = RequestState.Loading

            val result = repository.getCharacters()

            _state.value = result.fold(
                { RequestState.Error(it.message) },
                { RequestState.Completed(it) }
            )
        }
    }

    class Factory(private val repository: CharacterRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CharactersViewModel(repository) as T
        }
    }
}
