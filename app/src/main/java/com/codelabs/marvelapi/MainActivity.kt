package com.codelabs.marvelapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codelabs.marvelapi.databinding.MainActivityBinding
import com.codelabs.marvelapi.features.characters.CharactersFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, CharactersFragment.newInstance())
                    .commitNow()
        }
    }
}