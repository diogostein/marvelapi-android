package com.codelabs.marvelapi.features.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codelabs.marvelapi.R
import com.codelabs.marvelapi.core.Pagination
import com.codelabs.marvelapi.core.api.responses.CharacterResponse
import com.codelabs.marvelapi.core.models.Character

class CharactersAdapter
    : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    private val characters = mutableListOf<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)

        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = characters[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int = characters.size

    fun submitData(data: Pagination.PagingData<Character>) {
        characters.addAll(data.list)

        notifyDataSetChanged()
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

}