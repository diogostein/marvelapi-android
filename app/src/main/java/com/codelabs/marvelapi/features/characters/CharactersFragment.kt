package com.codelabs.marvelapi.features.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.codelabs.marvelapi.R
import com.codelabs.marvelapi.shared.pagination.Pagination
import com.codelabs.marvelapi.core.ResultState
import com.codelabs.marvelapi.core.models.Character
import com.codelabs.marvelapi.databinding.CharactersFragmentBinding
import com.codelabs.marvelapi.shared.pagination.PaginationController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharactersFragment : Fragment(R.layout.characters_fragment) {
    private val viewModel: CharactersViewModel by viewModels()

    @Inject
    lateinit var paginationController: PaginationController<Character, CharactersPagingAdapter.CharacterViewHolder>

    private var _binding: CharactersFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = CharactersFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = CharactersFragmentBinding.bind(view)

        with (paginationController) {
            layoutManager = GridLayoutManager(context, 3)
            setOnRetryClickListener { viewModel.getCharacters() }

            binding.resultStateView.let {
                it.setLayoutManager(layoutManager as GridLayoutManager)
                it.setAdapter(pagingAdapter)
                it.addOnScrollListener(getOnPagingScrollListener { viewModel.getCharacters() })
                it.setOnRetryClickListener { viewModel.getCharacters(true) }
            }
        }

        viewModel.state.observe(viewLifecycleOwner, { state ->
            paginationController.setIdle()

            when (state) {
                is ResultState.Loading -> onLoading()
                is ResultState.Error -> onError(state.message)
                is ResultState.PaginationLoading -> paginationController.setLoading()
                is ResultState.PaginationError -> paginationController.setError(state.message)
                is ResultState.PaginationFinished -> paginationController.setFinished()
                is ResultState.Completed<*> ->
                    onCompleted((state as ResultState.Completed<Pagination<Character>>).value)
            }
        })

        viewModel.getCharacters(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onLoading() = binding.resultStateView.showProgressIndicator()

    private fun onError(message: String) = binding.resultStateView.showError(message)

    private fun onCompleted(pagination: Pagination<Character>) {
        paginationController.setCompleted(pagination) {
            binding.resultStateView.showRecyclerView()
        }
    }
}