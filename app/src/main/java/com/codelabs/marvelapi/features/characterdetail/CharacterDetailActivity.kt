package com.codelabs.marvelapi.features.characterdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.codelabs.marvelapi.R
import com.codelabs.marvelapi.core.models.Character
import com.codelabs.marvelapi.core.replaceFragment
import com.codelabs.marvelapi.databinding.CharacterDetailActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailActivity : AppCompatActivity() {
    companion object {
        const val ARG_CHARACTER_ID = "CharacterDetailActivity.ARG_CHARACTER_ID"
    }

    private lateinit var binding: CharacterDetailActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_detail_activity)

        binding = CharacterDetailActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.collapsingToolbar.title = " "

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        }

        if (savedInstanceState == null) {
            val characterId = intent.extras?.getInt(ARG_CHARACTER_ID) ?: 0
            replaceFragment(CharacterDetailFragment.newInstance(characterId))
        }
    }

    fun fillAppBar(character: Character) {
        binding.collapsingToolbar.title = character.name

        Glide.with(this)
            .load(character.thumbnail?.url)
            .placeholder(R.drawable.marvel_placeholder_medium)
            .into(binding.ivCollapsing)
    }
}