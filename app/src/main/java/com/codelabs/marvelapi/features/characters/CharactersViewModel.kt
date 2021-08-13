package com.codelabs.marvelapi.features.characters

import androidx.lifecycle.*
import com.codelabs.marvelapi.core.RequestState
import com.codelabs.marvelapi.features.characters.data.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharacterRepository,
) : ViewModel() {
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

}
