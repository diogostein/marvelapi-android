package com.codelabs.marvelapi.features.characters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.codelabs.marvelapi.core.replaceFragment
import com.codelabs.marvelapi.databinding.CharactersActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersActivity : AppCompatActivity() {

    private lateinit var binding: CharactersActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = CharactersActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false);

        if (savedInstanceState == null) {
            replaceFragment(CharactersFragment.newInstance())
        }
    }
}