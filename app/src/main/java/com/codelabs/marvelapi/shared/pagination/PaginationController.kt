package com.codelabs.marvelapi.shared.pagination

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaginationController<T, VH : PagingAdapter.BinderViewHolder<T>>(
    val pagingAdapter: PagingAdapter<T, VH>,
    private val paginationScrollHandler: PaginationScrollHandler,
) {

    var layoutManager: LinearLayoutManager? = null
        set(layoutManager) {
            field = layoutManager

            if (field is GridLayoutManager) {
                val gridLayoutManager = field as GridLayoutManager
                gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return try {
                            val viewType = pagingAdapter.getItemViewType(position)
                            if (viewType == PagingAdapter.PagingDataHolder.ITEM) 1 else gridLayoutManager.spanCount
                        } catch (e: Exception) {
                            e.printStackTrace()
                            1
                        }
                    }
                }
            }
        }

    fun setOnItemClickListener(onItemClickListener: (value: T) -> Unit) {
        pagingAdapter.onItemClickListener = onItemClickListener
    }

    fun setOnRetryClickListener(onRetryClickListener: () -> Unit) {
        pagingAdapter.onRetryClickListener = onRetryClickListener
    }

    fun getOnPagingScrollListener(onLoadMoreItems: () -> Unit): RecyclerView.OnScrollListener {
        return paginationScrollHandler.onScrollListener(onLoadMoreItems)
    }

    private fun setScrollState(state: PaginationScrollHandler.State) {
        paginationScrollHandler.state = state
    }

    fun setIdle() {
        setScrollState(PaginationScrollHandler.State.Idle)
    }

    fun setLoading() {
        setScrollState(PaginationScrollHandler.State.Loading)
        pagingAdapter.showLoadingIndicator()
    }

    fun setError(message: String) = pagingAdapter.showError(message)

    fun setFinished(onPostFinished: (() -> Unit)? = null) {
        setScrollState(PaginationScrollHandler.State.Finished)
        pagingAdapter.showFinished()
        onPostFinished?.invoke()
    }

    fun setCompleted(pagination: Pagination<T>, onAdapterRefreshed: () -> Unit) {
        pagingAdapter.hideLoadingIndicator()

        val pagingData = pagination.collectLatest

        if (pagingData != null) {
            pagingAdapter.submitData(pagingData)
            onAdapterRefreshed.invoke()
        } else {
            setFinished()
        }
    }

}