package com.codelabs.marvelapi.features.characterdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.marvelapi.R
import com.codelabs.marvelapi.core.ResultState
import com.codelabs.marvelapi.core.models.Character
import com.codelabs.marvelapi.core.models.Comic
import com.codelabs.marvelapi.databinding.CharacterDetailFragmentBinding
import com.codelabs.marvelapi.features.characterdetail.adapters.ComicsPagingAdapter
import com.codelabs.marvelapi.shared.pagination.Pagination
import com.codelabs.marvelapi.shared.pagination.PaginationController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharacterDetailFragment : Fragment(R.layout.character_detail_fragment) {
    private var characterId: Int? = null
    private val viewModel: CharacterDetailViewModel by viewModels()

    private var _binding: CharacterDetailFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var paginationController: PaginationController<Comic, ComicsPagingAdapter.ComicViewHolder>

    companion object {
        private const val ARG_CHARACTER_ID = "ARG_CHARACTER_ID"

        fun newInstance(id: Int): CharacterDetailFragment {
            return CharacterDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CHARACTER_ID, id)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        characterId = arguments?.getInt(ARG_CHARACTER_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = CharacterDetailFragmentBinding.bind(view)

        paginationController.apply {
            val onGetCharacterComics: (Boolean) -> Unit =
                { viewModel.getCharacterComics(characterId!!, reload = it) }

            layoutManager = binding.hlvComics.layoutManager
            setOnRetryClickListener { onGetCharacterComics(false) }

            binding.hlvComics.let {
                it.setAdapter(pagingAdapter)
                it.addOnScrollListener(getOnPagingScrollListener { onGetCharacterComics(false) })
                it.setOnRetryClickListener { onGetCharacterComics(true) }
            }
        }

        binding.contentStateView.setOnRetryClickListener {
            viewModel.getCharacter(characterId!!)
        }

        viewModel.characterState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResultState.Loading -> binding.contentStateView.showProgressIndicator()
                is ResultState.Error -> binding.contentStateView.showError(state.message)
                is ResultState.Completed<*> ->
                    onCompleted((state as ResultState.Completed<Character>).value)
            }
        }

        viewModel.characterComicsState.observe(viewLifecycleOwner) { state ->
            paginationController.setIdle()

            when (state) {
                is ResultState.Loading -> binding.hlvComics.showProgressIndicator()
                is ResultState.Error -> binding.hlvComics.showError(state.message)
                is ResultState.PaginationLoading -> paginationController.setLoading()
                is ResultState.PaginationError -> paginationController.setError(state.message)
                is ResultState.PaginationFinished -> {
                    binding.hlvComics.showRecyclerView()
                    paginationController.setFinished()
                }
                is ResultState.Completed<*> ->
                    onComicsCompleted((state as ResultState.Completed<Pagination<Comic>>).value)
            }
        }

        viewModel.getCharacter(characterId!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onCompleted(character: Character) {
        (activity as CharacterDetailActivity).fillAppBar(character)

        binding.tvDescription.apply {
            if (character.description.isNotBlank()) {
                text = character.description
            } else {
                visibility = View.GONE
            }
        }

        binding.contentStateView.showContent()

        viewModel.getCharacterComics(character.id, reload = true)
    }

    private fun onComicsCompleted(pagination: Pagination<Comic>) {
        paginationController.setCompleted(pagination) {
            binding.hlvComics.showRecyclerView()
        }
    }
}