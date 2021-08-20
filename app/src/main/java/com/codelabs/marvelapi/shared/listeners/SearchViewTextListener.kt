package com.codelabs.marvelapi.shared.listeners

import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.*

class SearchViewTextListener(
    private val onQueryTextSubmit: (query: String?) -> Unit,
    private val onQueryTextChange: ((newText: String?) -> Unit)? = null,
) :  SearchView.OnQueryTextListener {
    private var coroutine: CoroutineScope? = null

    override fun onQueryTextSubmit(query: String?): Boolean {
        onQueryTextSubmit.invoke(if (query.isNullOrBlank()) null else query)

        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        coroutine?.cancel()
        coroutine = CoroutineScope(Dispatchers.Main)

        coroutine?.launch {
            delay(300)
            onQueryTextChange?.invoke(if (newText.isNullOrBlank()) null else newText)
        }

        return false
    }
}