package com.codelabs.marvelapi.features.characterdetail

import androidx.lifecycle.*
import arrow.core.Either
import com.codelabs.marvelapi.core.Const
import com.codelabs.marvelapi.core.ResultState
import com.codelabs.marvelapi.core.errors.Failure
import com.codelabs.marvelapi.core.models.*
import com.codelabs.marvelapi.features.characters.data.CharacterRepository
import com.codelabs.marvelapi.shared.pagination.Pager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: CharacterRepository,
) : ViewModel() {
    private val _characterState = MutableLiveData<ResultState<Character>>()
    val characterState: LiveData<ResultState<Character>> = _characterState

    private val _characterComicsState = MutableLiveData<ResultState<Pager<Comic>>>()
    val characterComicsState: LiveData<ResultState<Pager<Comic>>> = _characterComicsState
    private val _comicsPagination = Pager<Comic>(Const.HorizontalListViewPaging.PAGE_SIZE)

    private val _characterEventsState = MutableLiveData<ResultState<Pager<Event>>>()
    val characterEventsState: LiveData<ResultState<Pager<Event>>> = _characterEventsState
    private val _eventsPagination = Pager<Event>(Const.HorizontalListViewPaging.PAGE_SIZE)

    private val _characterSeriesState = MutableLiveData<ResultState<Pager<Serie>>>()
    val characterSeriesState: LiveData<ResultState<Pager<Serie>>> = _characterSeriesState
    private val _seriesPagination = Pager<Serie>(Const.HorizontalListViewPaging.PAGE_SIZE)

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

    fun getCharacterComics(characterId: Int, reload: Boolean = false) {
        buildPaginationState(_characterComicsState, _comicsPagination, reload) {
            repository.getCharacterComics(characterId, _comicsPagination.pageSize, _comicsPagination.offset)
        }
    }

    fun getCharacterEvents(characterId: Int, reload: Boolean = false) {
        buildPaginationState(_characterEventsState, _eventsPagination, reload) {
            repository.getCharacterEvents(characterId, _eventsPagination.pageSize, _eventsPagination.offset)
        }
    }

    fun getCharacterSeries(characterId: Int, reload: Boolean = false) {
        buildPaginationState(_characterSeriesState, _seriesPagination, reload) {
            repository.getCharacterSeries(characterId, _seriesPagination.pageSize, _seriesPagination.offset)
        }
    }

    private fun <T>buildPaginationState(
        state: MutableLiveData<ResultState<Pager<T>>>,
        pager: Pager<T>,
        reload: Boolean,
        onRepositoryResult: suspend () -> Either<Failure, List<T>>
    ) {
        if (reload) pager.reset()

        viewModelScope.launch {
            state.postValue(
                if (!reload) ResultState.PaginationLoading else ResultState.Loading
            )

            val result = onRepositoryResult()

            state.postValue(
                result.fold(
                    {
                        if (!reload) ResultState.PaginationError(it.message)
                        else ResultState.Error(it.message)
                    },
                    {
                        if (pager.refresh(it).hasReachedEndOfResults) ResultState.PaginationFinished
                        else ResultState.Completed(pager)
                    }
                )
            )
        }
    }
}
