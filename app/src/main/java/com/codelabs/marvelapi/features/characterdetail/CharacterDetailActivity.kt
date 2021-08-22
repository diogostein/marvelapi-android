package com.codelabs.marvelapi.features.characterdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.codelabs.marvelapi.R
import com.codelabs.marvelapi.core.models.Character
import com.codelabs.marvelapi.databinding.CharacterDetailActivityBinding
import com.codelabs.marvelapi.databinding.MainActivityBinding
import com.codelabs.marvelapi.features.characters.CharactersFragment
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

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(android.R.color.transparent)
        }

        if (savedInstanceState == null) {
            val characterId = intent.extras?.getInt(ARG_CHARACTER_ID) ?: 0

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, CharacterDetailFragment.newInstance(characterId))
                .commit()
        }
    }

    fun fillAppBar(character: Character) {
        println("printa poha!!!!")
        println(character.name)
        binding.collapsingToolbar.title = character.name

        Glide.with(this)
            .load(character.thumbnail.url)
            .placeholder(R.drawable.marvel_placeholder)
            .into(binding.ivCollapsing)
    }
}