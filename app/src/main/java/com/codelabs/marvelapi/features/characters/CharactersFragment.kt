package com.codelabs.marvelapi.features.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.codelabs.marvelapi.R
import com.codelabs.marvelapi.shared.pagination.Pagination
import com.codelabs.marvelapi.core.RequestState
import com.codelabs.marvelapi.core.models.Character
import com.codelabs.marvelapi.shared.pagination.PaginationController
import com.codelabs.marvelapi.shared.widgets.ResultStateView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharactersFragment : Fragment() {
    private val viewModel: CharactersViewModel by viewModels()

    @Inject
    lateinit var paginationController: PaginationController<Character, CharactersPagingAdapter.CharacterViewHolder>

    private lateinit var resultStateView: ResultStateView

    companion object {
        fun newInstance() = CharactersFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.characters_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paginationController.layoutManager = GridLayoutManager(context, 3)
        paginationController.setOnRetryClickListener { viewModel.getCharacters() }

        resultStateView = view.findViewById(R.id.resultStateView)
        resultStateView.setLayoutManager(paginationController.layoutManager as GridLayoutManager)
        resultStateView.setAdapter(paginationController.pagingAdapter)
        resultStateView.addOnScrollListener(
                paginationController.getOnPagingScrollListener { viewModel.getCharacters() })

        viewModel.state.observe(viewLifecycleOwner, { state ->
            paginationController.setIdle()

            when (state) {
                is RequestState.Loading -> onLoading()
                is RequestState.Error -> onError(state.message)
                is RequestState.PaginationLoading -> paginationController.setLoading()
                is RequestState.PaginationError -> paginationController.setError(state.message)
                is RequestState.PaginationFinished -> paginationController.setFinished()
                is RequestState.Completed<*> ->
                    onCompleted((state as RequestState.Completed<Pagination<Character>>).value)
            }
        })

        viewModel.getCharacters(true)
    }

    private fun onLoading() = resultStateView.showProgressIndicator()

    private fun onError(message: String) = resultStateView.showErrorMessage(message)

    private fun onCompleted(pagination: Pagination<Character>) {
        paginationController.setCompleted(pagination) {
            resultStateView.showRecyclerView()
        }
    }
}