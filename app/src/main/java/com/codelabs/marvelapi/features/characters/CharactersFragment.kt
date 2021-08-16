package com.codelabs.marvelapi.features.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.marvelapi.R
import com.codelabs.marvelapi.core.Pagination
import com.codelabs.marvelapi.core.RequestState
import com.codelabs.marvelapi.core.models.Character
import com.codelabs.marvelapi.shared.adapters.PagingAdapter
import com.codelabs.marvelapi.shared.handlers.PaginationScrollHandler
import com.codelabs.marvelapi.shared.widgets.CustomRecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : Fragment() {
    private val viewModel: CharactersViewModel by viewModels()
    private val adapter = CharactersPagingAdapter()
    private val paginationScrollHandler = PaginationScrollHandler()

    private lateinit var customRecyclerView: CustomRecyclerView

    companion object {
        fun newInstance() = CharactersFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.characters_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customRecyclerView = view.findViewById(R.id.rvCharacters)

        val gridLayoutManager = GridLayoutManager(context, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return try {
                    val viewType = adapter.getItemViewType(position)
                    if (viewType == PagingAdapter.PagingDataHolder.ITEM) 1 else 3
                } catch (e: Exception) {
                    e.printStackTrace()
                    1
                }
            }
        }

        customRecyclerView.setLayoutManager(gridLayoutManager)

        customRecyclerView.setAdapter(adapter)

        customRecyclerView.recyclerView.addOnScrollListener(
                paginationScrollHandler.onScrollListener(
                        customRecyclerView.recyclerView.layoutManager as LinearLayoutManager) {
            viewModel.getCharacters()
        })

        adapter.onRetryClickListener = { viewModel.getCharacters() }

        viewModel.state.observe(viewLifecycleOwner, { state ->
            paginationScrollHandler.state = PaginationScrollHandler.State.Idle

            when (state) {
                is RequestState.Loading -> onLoading()
                is RequestState.Error -> onError(state.message)
                is RequestState.PaginationLoading -> onPaginationLoading()
                is RequestState.PaginationError -> onPaginationError(state.message)
                is RequestState.PaginationFinished -> onPaginationFinished()
                is RequestState.Completed<*> ->
                    onPaginationCompleted((state as RequestState.Completed<Pagination<Character>>).value)
            }
        })

        viewModel.getCharacters(true)
    }

    private fun onLoading() = customRecyclerView.showProgressIndicator()

    private fun onError(message: String) = customRecyclerView.showErrorMessage(message)

    private fun onPaginationLoading() {
        paginationScrollHandler.state = PaginationScrollHandler.State.Loading
        adapter.showLoadingIndicator()
    }

    private fun onPaginationError(message: String) = adapter.showError(message)

    private fun onPaginationFinished() {
        paginationScrollHandler.state = PaginationScrollHandler.State.Finished
        adapter.showFinished()
    }

    private fun onPaginationCompleted(pagination: Pagination<Character>) {
        adapter.hideLoadingIndicator()

        val pagingData = pagination.collectLatest

        if (pagingData != null) {
            adapter.submitData(pagingData)
            customRecyclerView.showRecyclerView()
        }
    }
}