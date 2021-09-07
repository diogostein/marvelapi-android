package com.codelabs.marvelapi.features.characters

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.codelabs.marvelapi.R
import com.codelabs.marvelapi.core.Const
import com.codelabs.marvelapi.shared.pagination.Pager
import com.codelabs.marvelapi.core.ResultState
import com.codelabs.marvelapi.core.models.Character
import com.codelabs.marvelapi.databinding.CharactersFragmentBinding
import com.codelabs.marvelapi.features.characterdetail.CharacterDetailActivity
import com.codelabs.marvelapi.shared.listeners.SearchViewTextListener
import com.codelabs.marvelapi.shared.pagination.PagingController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharactersFragment : Fragment(R.layout.characters_fragment) {
    private val viewModel: CharactersViewModel by viewModels()

    var querySearch: String? = null

    @Inject
    lateinit var pagingController: PagingController<Character, CharactersPagingAdapter.CharacterViewHolder>

    private var _binding: CharactersFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = CharactersFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        if (savedInstanceState != null) {
            querySearch = savedInstanceState.getString("querySearch")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = CharactersFragmentBinding.bind(view)

        pagingController.apply {
            layoutManager = GridLayoutManager(context, Const.GridViewPaging.SPAN_COUNT)
            setOnRetryClickListener { viewModel.getCharacters(query = querySearch) }
            setOnItemClickListener {
                startActivity(Intent(context, CharacterDetailActivity::class.java).apply {
                    putExtras(Bundle().apply {
                        putInt(CharacterDetailActivity.ARG_CHARACTER_ID, it.id!!)
                    })
                })
            }

            binding.resultStateView.let {
                it.setLayoutManager(layoutManager as GridLayoutManager)
                it.setAdapter(pagingAdapter)
                it.addOnScrollListener(getOnPagingScrollListener { viewModel.getCharacters(query = querySearch) })
                it.setOnRetryClickListener { reload() }
                it.setOnRefreshListener { reload() }
            }
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            println(state.toString())
            when (state) {
                is ResultState.Loading -> onLoading()
                is ResultState.Error -> onError(state.message)
                is ResultState.PaginationLoading -> pagingController.setLoading()
                is ResultState.PaginationError -> pagingController.setError(state.message)
                is ResultState.PaginationFinished -> onPaginationFinished()
                is ResultState.Completed<*> ->
                    onCompleted((state as ResultState.Completed<Pager<Character>>).value)
            }
        }

        reload()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("querySearch", querySearch)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        val listener = SearchViewTextListener({
            searchView.clearFocus()
            querySearch = it
            reload()
        }, {
            querySearch = it
            if (querySearch.isNullOrBlank()) reload()
        })

        searchView.queryHint = getString(R.string.find_your_hero)
        searchView.setQuery(querySearch, false)
        searchView.setOnQueryTextListener(listener)
        searchView.setBackgroundColor(Color.TRANSPARENT)

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun reload() {
        pagingController.reset()
        viewModel.getCharacters(true, querySearch)
    }

    private fun onLoading() = binding.resultStateView.showProgressIndicator()

    private fun onError(message: String) {
        binding.resultStateView.showError(message)
    }

    private fun onCompleted(pager: Pager<Character>) {
        pagingController.setCompleted(pager) {
            binding.resultStateView.showRecyclerView()
        }
    }

    private fun onPaginationFinished() {
        binding.resultStateView.showRecyclerView()
        pagingController.setFinished()
    }
}