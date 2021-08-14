package com.codelabs.marvelapi.shared.handlers

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaginationScrollHandler {
    var state: State = State.Idle

    companion object {
        const val DISTANCE_TO_LOAD = 20
    }

    fun onScrollListener(layoutManager: LinearLayoutManager, onLoadMoreItems: () -> Unit): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount: Int = layoutManager.childCount
                val totalItemCount: Int = layoutManager.itemCount
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()

                Log.d("DS", "visibleItemCount: $visibleItemCount")
                Log.d("DS", "totalItemCount: $totalItemCount")
                Log.d("DS", "firstVisibleItemPosition: $firstVisibleItemPosition")

                if (state !is State.Loading && state !is State.Finished) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount - DISTANCE_TO_LOAD
                            && firstVisibleItemPosition >= 0) {
                        onLoadMoreItems.invoke()
                    }
                }
            }
        }
    }

    sealed class State {
        object Idle : State()
        object Loading : State()
        object Finished : State()
    }
}