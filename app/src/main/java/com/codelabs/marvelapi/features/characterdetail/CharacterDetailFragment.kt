package com.codelabs.marvelapi.features.characterdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.codelabs.marvelapi.R
import com.codelabs.marvelapi.core.ResultState
import com.codelabs.marvelapi.core.models.*
import com.codelabs.marvelapi.databinding.CharacterDetailFragmentBinding
import com.codelabs.marvelapi.features.characterdetail.adapters.ComicsPagingAdapter
import com.codelabs.marvelapi.features.characterdetail.adapters.EventsPagingAdapter
import com.codelabs.marvelapi.features.characterdetail.adapters.SeriesPagingAdapter
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
    lateinit var comicsPaginationController:
            PaginationController<Comic, ComicsPagingAdapter.ComicViewHolder>

    @Inject
    lateinit var eventsPaginationController:
            PaginationController<Event, EventsPagingAdapter.EventViewHolder>

    @Inject
    lateinit var seriesPaginationController:
            PaginationController<Serie, SeriesPagingAdapter.SerieViewHolder>

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

        setupCharacter()
        setupComics()
        setupEvents()
        setupSeries()

        viewModel.getCharacter(characterId!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupCharacter() {
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
        viewModel.getCharacterEvents(character.id, reload = true)
        viewModel.getCharacterSeries(character.id, reload = true)
    }

    private fun setupComics() {
        comicsPaginationController.apply {
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

        viewModel.characterComicsState.observe(viewLifecycleOwner) { state ->
            comicsPaginationController.setIdle()

            when (state) {
                is ResultState.Loading -> binding.hlvComics.showProgressIndicator()
                is ResultState.Error -> binding.hlvComics.showError(state.message)
                is ResultState.PaginationLoading -> comicsPaginationController.setLoading()
                is ResultState.PaginationError -> comicsPaginationController.setError(state.message)
                is ResultState.PaginationFinished -> {
                    comicsPaginationController.setFinished {
                        binding.hlvComics.showRecyclerView()
                    }
                }
                is ResultState.Completed<*> -> {
                    comicsPaginationController.setCompleted(
                        (state as ResultState.Completed<Pagination<Comic>>).value) {
                        binding.hlvComics.showRecyclerView()
                    }
                }
            }
        }
    }

    private fun setupEvents() {
        eventsPaginationController.apply {
            val onGetCharacterEvents: (Boolean) -> Unit =
                { viewModel.getCharacterEvents(characterId!!, reload = it) }

            layoutManager = binding.hlvEvents.layoutManager
            setOnRetryClickListener { onGetCharacterEvents(false) }

            binding.hlvEvents.let {
                it.setAdapter(pagingAdapter)
                it.addOnScrollListener(getOnPagingScrollListener { onGetCharacterEvents(false) })
                it.setOnRetryClickListener { onGetCharacterEvents(true) }
            }
        }

        viewModel.characterEventsState.observe(viewLifecycleOwner) { state ->
            eventsPaginationController.setIdle()

            when (state) {
                is ResultState.Loading -> binding.hlvEvents.showProgressIndicator()
                is ResultState.Error -> binding.hlvEvents.showError(state.message)
                is ResultState.PaginationLoading -> eventsPaginationController.setLoading()
                is ResultState.PaginationError -> eventsPaginationController.setError(state.message)
                is ResultState.PaginationFinished -> {
                    eventsPaginationController.setFinished {
                        binding.hlvEvents.showRecyclerView()
                    }
                }
                is ResultState.Completed<*> -> {
                    eventsPaginationController.setCompleted(
                        (state as ResultState.Completed<Pagination<Event>>).value) {
                        binding.hlvEvents.showRecyclerView()
                    }
                }
            }
        }
    }

    private fun setupSeries() {
        seriesPaginationController.apply {
            val onGetCharacterSeries: (Boolean) -> Unit =
                { viewModel.getCharacterSeries(characterId!!, reload = it) }

            layoutManager = binding.hlvSeries.layoutManager
            setOnRetryClickListener { onGetCharacterSeries(false) }

            binding.hlvSeries.let {
                it.setAdapter(pagingAdapter)
                it.addOnScrollListener(getOnPagingScrollListener { onGetCharacterSeries(false) })
                it.setOnRetryClickListener { onGetCharacterSeries(true) }
            }
        }

        viewModel.characterSeriesState.observe(viewLifecycleOwner) { state ->
            seriesPaginationController.setIdle()

            when (state) {
                is ResultState.Loading -> binding.hlvSeries.showProgressIndicator()
                is ResultState.Error -> binding.hlvSeries.showError(state.message)
                is ResultState.PaginationLoading -> seriesPaginationController.setLoading()
                is ResultState.PaginationError -> seriesPaginationController.setError(state.message)
                is ResultState.PaginationFinished -> {
                    seriesPaginationController.setFinished {
                        binding.hlvSeries.showRecyclerView()
                    }
                }
                is ResultState.Completed<*> -> {
                    seriesPaginationController.setCompleted(
                        (state as ResultState.Completed<Pagination<Serie>>).value) {
                        binding.hlvSeries.showRecyclerView()
                    }
                }
            }
        }
    }
}