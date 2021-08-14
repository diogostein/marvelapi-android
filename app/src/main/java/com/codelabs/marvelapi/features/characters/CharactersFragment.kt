package com.codelabs.marvelapi.features.characters

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.marvelapi.R
import com.codelabs.marvelapi.core.Pagination
import com.codelabs.marvelapi.core.RequestState
import com.codelabs.marvelapi.core.castValue
import com.codelabs.marvelapi.core.models.Character
import com.codelabs.marvelapi.shared.handlers.PaginationScrollHandler
import com.codelabs.marvelapi.shared.widgets.CustomRecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : Fragment() {
    private val viewModel: CharactersViewModel by viewModels()
    private val adapter = CharactersAdapter()
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
        customRecyclerView.setLayoutManager(GridLayoutManager(context, 3))
        customRecyclerView.setAdapter(adapter)

        customRecyclerView.recyclerView.addOnScrollListener(
                paginationScrollHandler.onScrollListener(
                        customRecyclerView.recyclerView.layoutManager as LinearLayoutManager) {
                    Log.d("DS", "onLoadMore")
            viewModel.getCharacters()
        })

        viewModel.state.observe(viewLifecycleOwner, { state ->
            paginationScrollHandler.state = PaginationScrollHandler.State.Idle

            when (state) {
                is RequestState.Loading -> customRecyclerView.showProgressIndicator()
                is RequestState.Error -> customRecyclerView.showErrorMessage(state.message)
                is RequestState.PaginationLoading -> {
                    paginationScrollHandler.state = PaginationScrollHandler.State.Loading
                }
                is RequestState.PaginationError -> {
                    Toast.makeText(context, "Pagination error: ${state.message}", Toast.LENGTH_SHORT).show()
                }
                is RequestState.PaginationEnded -> {
                    paginationScrollHandler.state = PaginationScrollHandler.State.Finished
                    Toast.makeText(context, "Pagination ended!", Toast.LENGTH_SHORT).show()
                }
                is RequestState.Completed<*> -> {
                    val pagination = state.castValue<Pagination<Character>>().value
                    val pagingData = pagination.collectLatest

                    if (pagingData != null) {
                        adapter.submitData(pagingData)
                        customRecyclerView.showRecyclerView()
                    }
                }
            }
        })

        viewModel.getCharacters(true)
    }
}