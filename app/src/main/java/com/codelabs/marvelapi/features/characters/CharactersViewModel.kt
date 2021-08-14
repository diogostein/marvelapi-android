package com.codelabs.marvelapi.features.characters

import androidx.lifecycle.*
import com.codelabs.marvelapi.core.Pagination
import com.codelabs.marvelapi.core.RequestState
import com.codelabs.marvelapi.core.models.Character
import com.codelabs.marvelapi.features.characters.data.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharacterRepository,
) : ViewModel() {
    private val _state = MutableLiveData<RequestState>()
    private val _pagination = Pagination<Character>(40)

    val state: LiveData<RequestState> = _state

    fun getCharacters(reload: Boolean = false) {
        if (reload) _pagination.reset()

        viewModelScope.launch {
            _state.value = if (!reload) RequestState.PaginationLoading else RequestState.Loading

            val result = repository.getCharacters(_pagination.pageSize, _pagination.offset)

            _state.value = result.fold(
                {
                    if (!reload) RequestState.PaginationError(it.message)
                    else RequestState.Error(it.message)
                },
                {
                    if (_pagination.refresh(it).hasReachedEndOfResults) RequestState.PaginationEnded
                    else RequestState.Completed(_pagination)
                }
            )
        }
    }
}
