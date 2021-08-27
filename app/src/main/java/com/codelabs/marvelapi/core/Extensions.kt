package com.codelabs.marvelapi.core

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.codelabs.marvelapi.R

fun AppCompatActivity.replaceFragment(fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .replace(R.id.container, fragment)
        .commit()
}