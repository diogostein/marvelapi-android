package com.codelabs.marvelapi.features.characters

import androidx.lifecycle.*
import com.codelabs.marvelapi.shared.pagination.Pagination
import com.codelabs.marvelapi.core.ResultState
import com.codelabs.marvelapi.core.models.Character
import com.codelabs.marvelapi.features.characters.data.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharacterRepository,
) : ViewModel() {
    private val _state = MutableLiveData<ResultState<Pagination<Character>>>()
    private val _pagination = Pagination<Character>(40)

    val state: LiveData<ResultState<Pagination<Character>>> = _state

    fun getCharacters(reload: Boolean = false, query: String? = null) {
        if (reload) _pagination.reset()

        viewModelScope.launch {
            _state.postValue(
                if (!reload) ResultState.PaginationLoading else ResultState.Loading
            )

            val result = repository.getCharacters(_pagination.pageSize, _pagination.offset, query)

            _state.postValue(
                result.fold(
                    {
                        if (!reload) ResultState.PaginationError(it.message)
                        else ResultState.Error(it.message)
                    },
                    {
                        if (_pagination.refresh(it).hasReachedEndOfResults) ResultState.PaginationFinished
                        else ResultState.Completed(_pagination)
                    }
                )
            )
        }
    }
}
