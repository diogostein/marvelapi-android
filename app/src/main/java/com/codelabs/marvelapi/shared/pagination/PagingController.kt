package com.codelabs.marvelapi.shared.pagination

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PagingController<T, VH : PagingAdapter.BinderViewHolder<T>>(
    val pagingAdapter: PagingAdapter<T, VH>,
    private val paginationScrollHandler: PagingScrollHandler,
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

    private fun setScrollState(state: PagingScrollHandler.State) {
        paginationScrollHandler.state = state
    }

    fun setLoading() {
        setScrollState(PagingScrollHandler.State.Blocked)
        pagingAdapter.showLoadingIndicator()
    }

    fun setError(message: String) = pagingAdapter.showError(message)

    fun setFinished(onPostFinished: (() -> Unit)? = null) {
        setScrollState(PagingScrollHandler.State.Blocked)
        pagingAdapter.showFinished()
        onPostFinished?.invoke()
    }

    fun setCompleted(pager: Pager<T>, onAdapterRefreshed: () -> Unit) {
        pagingAdapter.hideLoadingIndicator()

        val pagingData = pager.collectLatest

        if (pagingData != null) {
            pagingAdapter.submitData(pagingData)
            onAdapterRefreshed.invoke()
            setScrollState(PagingScrollHandler.State.Idle)
        } else {
            setFinished()
        }
    }

    fun reset() {
        pagingAdapter.clear()
    }

}