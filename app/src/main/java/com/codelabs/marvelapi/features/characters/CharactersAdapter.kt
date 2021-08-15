package com.codelabs.marvelapi.features.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codelabs.marvelapi.R
import com.codelabs.marvelapi.core.Pagination
import com.codelabs.marvelapi.core.models.Character

class CharactersAdapter
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<PaginationData<Character>>()

    var onRetryClickListener: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PaginationData.LOADING -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_paging_loading, parent, false)
                LoadingViewHolder(view)
            }
            PaginationData.ERROR -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_paging_error, parent, false)
                ErrorViewHolder(view)
            }
            PaginationData.FINISHED -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_paging_finished, parent, false)
                FinishedViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
                CharacterViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        when (holder.itemViewType) {
            PaginationData.ITEM -> (holder as CharacterViewHolder).bind((item as PaginationData.Item).value)
            PaginationData.ERROR -> (holder as ErrorViewHolder).bind((item as PaginationData.Error))
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].itemViewType
    }

    fun submitData(data: Pagination.PagingData<Character>) {
        //characters.addAll(data.list)
        items.addAll(data.list.map { PaginationData.Item(it) })

        notifyDataSetChanged()
    }

    fun showLoadingIndicator() {
        hideError()
        hideFinished()

        if (items.find { it is PaginationData.Loading } == null) {
            items.add(itemCount, PaginationData.Loading)
            notifyItemChanged(itemCount - 1)
        }
    }

    fun hideLoadingIndicator() {
        val index = items.indexOfFirst { it is PaginationData.Loading }

        if (index != -1) {
            items.removeAt(index)
            notifyItemChanged(index)
        }
    }

    fun showError(message: String) {
        hideLoadingIndicator()
        hideFinished()

        if (items.find { it is PaginationData.Error } == null) {
            items.add(itemCount, PaginationData.Error(message, onRetryClickListener))
            notifyItemChanged(itemCount - 1)
        }
    }

    fun hideError() {
        val index = items.indexOfFirst { it is PaginationData.Error }

        if (index != -1) {
            items.removeAt(index)
            notifyItemChanged(index)
        }
    }

    fun showFinished() {
        hideLoadingIndicator()
        hideError()

        if (items.find { it is PaginationData.Finished } == null) {
            items.add(itemCount, PaginationData.Finished)
            notifyItemChanged(itemCount - 1)
        }
    }

    fun hideFinished() {
        val index = items.indexOfFirst { it is PaginationData.Finished }

        if (index != -1) {
            items.removeAt(index)
            notifyItemChanged(index)
        }
    }

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivAvatar = itemView.findViewById<ImageView>(R.id.ivAvatar)
        private val tvName = itemView.findViewById<TextView>(R.id.tvName)

        fun bind(character: Character) {
            tvName.text = character.name

            Glide.with(itemView)
                .load(character.thumbnail.url)
                .placeholder(R.drawable.ic_launcher_background)
                .into(ivAvatar)
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class FinishedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class ErrorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvMessage = itemView.findViewById<TextView>(R.id.tvMessage)
        private val btnReload = itemView.findViewById<AppCompatButton>(R.id.btnReload)

        fun bind(error: PaginationData.Error) {
            tvMessage.text = error.message
            btnReload.setOnClickListener { error.onRetryClickListener?.invoke() }
        }
    }

    sealed class PaginationData<out T>(val itemViewType: Int) {
        companion object {
            const val ITEM = 0
            const val LOADING = 1
            const val ERROR = 3
            const val FINISHED = 4
        }

        data class Item<I>(val value: I) : PaginationData<I>(ITEM)
        object Loading : PaginationData<Nothing>(LOADING)
        data class Error(val message: String, val onRetryClickListener: (() -> Unit)? = null) : PaginationData<Nothing>(ERROR)
        object Finished : PaginationData<Nothing>(FINISHED)
    }

}