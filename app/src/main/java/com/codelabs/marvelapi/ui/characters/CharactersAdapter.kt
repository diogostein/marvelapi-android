package com.codelabs.marvelapi.ui.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.marvelapi.R
import com.codelabs.marvelapi.core.api.responses.CharacterResponse

class CharactersAdapter(private val characters: List<CharacterResponse>)
    : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)

        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = characters[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int = characters.size

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName = itemView.findViewById<TextView>(R.id.tvName)

        fun bind(character: CharacterResponse) {
            tvName.text = character.name
        }
    }

}