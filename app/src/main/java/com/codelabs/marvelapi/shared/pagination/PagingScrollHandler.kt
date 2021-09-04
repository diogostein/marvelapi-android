package com.codelabs.marvelapi.shared.pagination

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PagingScrollHandler {
    var state: State = State.Idle

    companion object {
        const val DISTANCE_TO_LOAD = 6
    }

    fun onScrollListener(onLoadMoreItems: () -> Unit): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager

                val visibleItemCount: Int = layoutManager.childCount
                val totalItemCount: Int = layoutManager.itemCount
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()

//                Log.d("DS", "visibleItemCount: $visibleItemCount")
//                Log.d("DS", "totalItemCount: $totalItemCount")
//                Log.d("DS", "firstVisibleItemPosition: $firstVisibleItemPosition")

                if (state is State.Idle) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount - DISTANCE_TO_LOAD
                            && firstVisibleItemPosition >= 0) {
                        state = State.Blocked
                        onLoadMoreItems.invoke()
                    }
                }
            }
        }
    }

    sealed class State {
        object Idle : State()
        object Blocked : State()
    }
}