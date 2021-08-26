package com.codelabs.marvelapi.features.characterdetail.adapters

import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.codelabs.marvelapi.R
import com.codelabs.marvelapi.core.models.Event
import com.codelabs.marvelapi.databinding.ItemEventBinding
import com.codelabs.marvelapi.shared.pagination.PagingAdapter

class EventsPagingAdapter : PagingAdapter<Event, EventsPagingAdapter.EventViewHolder>() {

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(inflate(parent, R.layout.item_event))
    }

    override fun onBindItemViewHolder(holder: EventViewHolder, position: Int) {
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

    class EventViewHolder(itemView: View) : BinderViewHolder<Event>(itemView) {
        private val binding = ItemEventBinding.bind(itemView)

        override lateinit var value: Event

        override fun bind(value: Event) {
            this.value = value

            Glide.with(itemView)
                .load(value.thumbnail.url)
                .placeholder(R.drawable.comics_placeholder)
                .into(binding.ivEvent)
        }
    }

}