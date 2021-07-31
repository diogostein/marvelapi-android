package com.codelabs.marvelapi.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelabs.marvelapi.core.api.MarvelApi
import com.codelabs.marvelapi.core.api.RequestState
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {
    private val _state = MutableLiveData<RequestState>()
    val state: LiveData<RequestState> = _state

    fun getCharacters() {
        viewModelScope.launch {
            _state.value = RequestState.Loading

            val response = MarvelApi.service.getCharacters()

            if (response.isSuccessful) {
                _state.value = RequestState.Completed(response.body())
            } else {
                _state.value = RequestState.Error("Erro")
            }
        }
    }
}