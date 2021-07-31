package com.codelabs.marvelapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codelabs.marvelapi.ui.characters.CharactersFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, CharactersFragment.newInstance())
                    .commitNow()
        }
    }
}