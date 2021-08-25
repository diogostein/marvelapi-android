package com.codelabs.marvelapi.features.characterdetail

import androidx.lifecycle.*
import com.codelabs.marvelapi.core.ResultState
import com.codelabs.marvelapi.core.models.Character
import com.codelabs.marvelapi.core.models.Comic
import com.codelabs.marvelapi.features.characters.data.CharacterRepository
import com.codelabs.marvelapi.shared.pagination.Pagination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: CharacterRepository,
) : ViewModel() {
    private val _characterState = MutableLiveData<ResultState<Character>>()
    val characterState: LiveData<ResultState<Character>> = _characterState

    private val _characterComicsState = MutableLiveData<ResultState<Pagination<Comic>>>()
    val characterComicsState: LiveData<ResultState<Pagination<Comic>>> = _characterComicsState

    private val _comicsPagination = Pagination<Comic>(40)

    fun getCharacter(id: Int) {
        viewModelScope.launch {
            _characterState.postValue(ResultState.Loading)

            val result = repository.getCharacter(id)

            _characterState.postValue(
                result.fold(
                    { ResultState.Error(it.message) },
                    { ResultState.Completed(it) }
                )
            )
        }
    }

//    fun getCharacterComics(characterId: Int) {
//        viewModelScope.launch {
//            _characterComicsState.postValue(ResultState.Loading)
//
//            val result = repository.getCharacterComics(characterId, limit = 20, offset = 0)
//
//            _characterComicsState.postValue(
//                result.fold(
//                    { ResultState.Error(it.message) },
//                    { ResultState.Completed(it) }
//                )
//            )
//        }
//    }

    fun getCharacterComics(characterId: Int, reload: Boolean = false) {
        if (reload) _comicsPagination.reset()

        viewModelScope.launch {
            _characterComicsState.postValue(
                if (!reload) ResultState.PaginationLoading else ResultState.Loading
            )

            val result = repository.getCharacterComics(
                characterId, _comicsPagination.pageSize, _comicsPagination.offset)

            _characterComicsState.postValue(
                result.fold(
                    {
                        if (!reload) ResultState.PaginationError(it.message)
                        else ResultState.Error(it.message)
                    },
                    {
                        if (_comicsPagination.refresh(it).hasReachedEndOfResults) ResultState.PaginationFinished
                        else ResultState.Completed(_comicsPagination)
                    }
                )
            )
        }
    }
}
