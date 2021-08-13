package com.codelabs.marvelapi.features.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.codelabs.marvelapi.R
import com.codelabs.marvelapi.core.RequestState
import com.codelabs.marvelapi.core.castValue
import com.codelabs.marvelapi.core.models.Character
import com.codelabs.marvelapi.shared.widgets.CustomRecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : Fragment() {
    private val viewModel: CharactersViewModel by viewModels()

    private lateinit var rvCharacters: CustomRecyclerView

    companion object {
        fun newInstance() = CharactersFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.characters_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvCharacters = view.findViewById(R.id.rvCharacters)

        viewModel.state.observe(viewLifecycleOwner, { state ->
            when (state) {
                is RequestState.Loading -> rvCharacters.setLoading()
                is RequestState.Error -> rvCharacters.setMessage(state.message)
                is RequestState.Completed<*> -> {
                    val value = state.castValue<List<Character>>().value
                    rvCharacters.setRecyclerView(CharactersAdapter(value))
                }
            }
        })

        viewModel.getCharacters()
    }
}