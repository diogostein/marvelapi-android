package com.codelabs.marvelapi.features.characterdetail.adapters

import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.codelabs.marvelapi.R
import com.codelabs.marvelapi.core.models.Serie
import com.codelabs.marvelapi.databinding.ItemSerieBinding
import com.codelabs.marvelapi.shared.pagination.PagingAdapter

class SeriesPagingAdapter : PagingAdapter<Serie, SeriesPagingAdapter.SerieViewHolder>() {

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): SerieViewHolder {
        return SerieViewHolder(inflate(parent, R.layout.item_serie))
    }

    override fun onBindItemViewHolder(holder: SerieViewHolder, position: Int) {
        val item = items[position]

        holder.bind((item as PagingDataHolder.Item).value)
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

    class SerieViewHolder(itemView: View) : BinderViewHolder<Serie>(itemView) {
        private val binding = ItemSerieBinding.bind(itemView)

        override lateinit var value: Serie

        override fun bind(value: Serie) {
            this.value = value

            Glide.with(itemView)
                .load(value.thumbnail?.url)
                .placeholder(R.drawable.comics_placeholder)
                .into(binding.ivSerie)
        }
    }

}