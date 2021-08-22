package com.codelabs.marvelapi.shared.pagination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.marvelapi.R
import com.codelabs.marvelapi.core.models.Character
import com.codelabs.marvelapi.databinding.ItemPagingErrorBinding

abstract class PagingAdapter<T, VH : PagingAdapter.BinderViewHolder<T>>
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected val items = mutableListOf<PagingDataHolder<T>>()

    var onItemClickListener: ((value: T) -> Unit)? = null
    var onRetryClickListener: (() -> Unit)? = null

    abstract fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): VH

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PagingDataHolder.LOADING  -> LoadingViewHolder(inflate(parent, R.layout.item_paging_loading))
            PagingDataHolder.ERROR    -> ErrorViewHolder(inflate(parent, R.layout.item_paging_error))
            PagingDataHolder.FINISHED -> FinishedViewHolder(inflate(parent, R.layout.item_paging_finished))
            else                      -> onCreateItemViewHolder(parent, viewType)
        }
    }

    abstract fun onBindItemViewHolder(holder: VH, position: Int)

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        when (holder.itemViewType) {
            PagingDataHolder.ITEM  -> onBindItemViewHolder(holder as VH, position)
            PagingDataHolder.ERROR -> (holder as ErrorViewHolder).bind((item as PagingDataHolder.Error))
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].itemViewType
    }

    protected fun inflate(parent: ViewGroup, layoutRes: Int): View {
        return LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
    }

    fun submitData(data: Pagination.PagingData<T>) {
        items.addAll(data.list.map { PagingDataHolder.Item(it) })
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun showLoadingIndicator() {
        hideError()
        hideFinished()

        if (items.find { it is PagingDataHolder.Loading } == null) {
            items.add(itemCount, PagingDataHolder.Loading)
            notifyItemChanged(itemCount - 1)
        }
    }

    fun hideLoadingIndicator() {
        val index = items.indexOfFirst { it is PagingDataHolder.Loading }

        if (index != -1) {
            items.removeAt(index)
            notifyItemChanged(index)
        }
    }

    fun showError(message: String) {
        hideLoadingIndicator()
        hideFinished()

        if (items.find { it is PagingDataHolder.Error } == null) {
            items.add(itemCount, PagingDataHolder.Error(message, onRetryClickListener))
            notifyItemChanged(itemCount - 1)
        }
    }

    fun hideError() {
        val index = items.indexOfFirst { it is PagingDataHolder.Error }

        if (index != -1) {
            items.removeAt(index)
            notifyItemChanged(index)
        }
    }

    fun showFinished() {
        hideLoadingIndicator()
        hideError()

        if (items.find { it is PagingDataHolder.Finished } == null) {
            items.add(itemCount, PagingDataHolder.Finished)
            notifyItemChanged(itemCount - 1)
        }
    }

    fun hideFinished() {
        val index = items.indexOfFirst { it is PagingDataHolder.Finished }

        if (index != -1) {
            items.removeAt(index)
            notifyItemChanged(index)
        }
    }

    abstract class BinderViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        protected abstract var value: T
        abstract fun bind(value: T)
        open fun setOnItemClickListener(listener: ((value: T) -> Unit)? = null) {}
    }

    private class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private class FinishedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private class ErrorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPagingErrorBinding.bind(itemView)

        fun bind(error: PagingDataHolder.Error) {
            binding.tvMessage.text = error.message
            binding.btnReload.setOnClickListener { error.onRetryClickListener?.invoke() }
        }
    }

    sealed class PagingDataHolder<out T>(val itemViewType: Int) {
        companion object {
            const val ITEM = 0
            const val LOADING = 1
            const val ERROR = 3
            const val FINISHED = 4
        }

        data class Item<I>(val value: I) : PagingDataHolder<I>(ITEM)

        object Loading : PagingDataHolder<Nothing>(LOADING)

        data class Error(
            val message: String,
            val onRetryClickListener: (() -> Unit)? = null,
        ) : PagingDataHolder<Nothing>(ERROR)

        object Finished : PagingDataHolder<Nothing>(FINISHED)
    }

}