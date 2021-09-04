package com.codelabs.marvelapi.shared.pagination

class Pager<T>(val pageSize: Int) {
    private var _nextPage = 1
    private var _hasReachedEndOfResults = false
    private val _listHolder = mutableListOf<PagingData<T>>()

    val hasReachedEndOfResults: Boolean get() = _hasReachedEndOfResults
    val collectLatest: PagingData<T>? get() = _listHolder.lastOrNull()
    val offset: Int get() = pageSize * (_nextPage - 1)

    fun refresh(list: List<T>): Pager<T> {
        _hasReachedEndOfResults = list.isEmpty()

        if (!hasReachedEndOfResults) {
            _listHolder.add(PagingData(_nextPage, list))
            _nextPage++
        }

        return this
    }

    fun reset() {
        _nextPage = 1
        _listHolder.clear()
        _hasReachedEndOfResults = false
    }

    data class PagingData<T>(val page: Int, val list: List<T>)
}