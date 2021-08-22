package com.codelabs.marvelapi.features.characterdetail

import androidx.lifecycle.*
import com.codelabs.marvelapi.core.ResultState
import com.codelabs.marvelapi.core.models.Character
import com.codelabs.marvelapi.features.characters.data.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: CharacterRepository,
) : ViewModel() {
    private val _state = MutableLiveData<ResultState<Character>>()
    val state: LiveData<ResultState<Character>> = _state

    fun getCharacter(id: Int) {
        viewModelScope.launch {
            _state.postValue(ResultState.Loading)

            val result = repository.getCharacter(id)

            _state.postValue(
                result.fold(
                    { ResultState.Error(it.message) },
                    { ResultState.Completed(it) }
                )
            )
        }
    }
}
