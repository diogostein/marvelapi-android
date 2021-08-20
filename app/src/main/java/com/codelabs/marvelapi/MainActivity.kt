package com.codelabs.marvelapi

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
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

        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, CharactersFragment.newInstance())
                    .commitNow()
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.search_menu, menu)
////
////        val searchItem = menu.findItem(R.id.action_search)
////        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
////        val searchView = searchItem.actionView as SearchView
////
////        //searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
////
////        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
////            override fun onQueryTextSubmit(query: String?): Boolean {
////                println(query)
////
////                return false
////            }
////
////            override fun onQueryTextChange(newText: String?): Boolean {
////                println(newText)
////
////                return false
////            }
////
////        })
//
//        return super.onCreateOptionsMenu(menu)
//    }
}