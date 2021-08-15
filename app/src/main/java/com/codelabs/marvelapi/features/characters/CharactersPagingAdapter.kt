package com.codelabs.marvelapi.features.characters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codelabs.marvelapi.R
import com.codelabs.marvelapi.core.models.Character
import com.codelabs.marvelapi.shared.adapters.PagingAdapter

class CharactersPagingAdapter
    : PagingAdapter<Character, CharactersPagingAdapter.CharacterViewHolder>() {

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(inflate(parent, R.layout.item_character))
    }

    override fun onBindItemViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = items[position]

        holder.bind((item as PagingDataHolder.Item).value)
    }

    class CharacterViewHolder(itemView: View) : BinderViewHolder<Character>(itemView) {
        private val ivAvatar = itemView.findViewById<ImageView>(R.id.ivAvatar)
        private val tvName = itemView.findViewById<TextView>(R.id.tvName)

        override fun bind(value: Character) {
            tvName.text = value.name

            Glide.with(itemView)
                    .load(value.thumbnail.url)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(ivAvatar)
        }
    }

}