package com.codelabs.marvelapi.features.characterdetail.adapters

import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.codelabs.marvelapi.R
import com.codelabs.marvelapi.core.models.Comic
import com.codelabs.marvelapi.databinding.ItemComicBinding
import com.codelabs.marvelapi.shared.pagination.PagingAdapter

class ComicsPagingAdapter : PagingAdapter<Comic, ComicsPagingAdapter.ComicViewHolder>() {

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        return ComicViewHolder(inflate(parent, R.layout.item_comic))
    }

    override fun onBindItemViewHolder(holder: ComicViewHolder, position: Int) {
        val item = items[position]

        holder.bind((item as PagingDataHolder.Item).value)
        holder.setOnItemClickListener(onItemClickListener)
    }

    override fun onBindLoadingViewHolder(holder: LoadingViewHolder, position: Int) {
        super.onBindLoadingViewHolder(holder, position)
        holder.setHorizontalMode()
    }

    override fun onBindErrorViewHolder(holder: ErrorViewHolder, position: Int) {
        super.onBindErrorViewHolder(holder, position)
        holder.setHorizontalMode()
    }

    override fun onBindFinishedViewHolder(holder: FinishedViewHolder, position: Int) {
        super.onBindFinishedViewHolder(holder, position)
        holder.setHorizontalMode()
    }

    class ComicViewHolder(itemView: View) : BinderViewHolder<Comic>(itemView) {
        private val binding = ItemComicBinding.bind(itemView)

        override lateinit var value: Comic

        override fun bind(value: Comic) {
            this.value = value

            Glide.with(itemView)
                .load(value.thumbnail?.url)
                .placeholder(R.drawable.comics_placeholder)
                .into(binding.ivComic)
        }
    }

}