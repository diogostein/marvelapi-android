package com.codelabs.marvelapi.shared.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.marvelapi.databinding.HorizontalListViewBinding

class HorizontalListView constructor(
    context: Context,
    attrs: AttributeSet?,
) : FrameLayout(context, attrs) {
    private val binding = HorizontalListViewBinding.inflate(LayoutInflater.from(context), this)

    init {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false)
            isNestedScrollingEnabled = false
        }
    }

    val layoutManager get() = binding.recyclerView.layoutManager as LinearLayoutManager

    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        binding.recyclerView.adapter = adapter
    }

    fun addOnScrollListener(onScrollListener: RecyclerView.OnScrollListener) {
        binding.recyclerView.addOnScrollListener(onScrollListener)
    }

    fun setOnRetryClickListener(onRetryClickListener: OnClickListener) {
        binding.contentStateView.includeError.btnReload.setOnClickListener(onRetryClickListener)
    }

    fun showRecyclerView(adapter: RecyclerView.Adapter<*>? = null) {
        binding.contentStateView.includeProgress.progressBar.visibility = GONE
        setRecyclerViewVisibility(VISIBLE)
        setErrorVisibility(GONE)
        if (adapter != null) setAdapter(adapter)
    }

    fun showProgressIndicator() {
        binding.contentStateView.includeProgress.progressBar.visibility = VISIBLE
        setRecyclerViewVisibility(GONE)
        setErrorVisibility(GONE)
    }

    fun showError(message: String? = null) {
        binding.contentStateView.includeProgress.progressBar.visibility = GONE
        setRecyclerViewVisibility(GONE)
        setErrorVisibility(VISIBLE, message)
    }

    private fun setRecyclerViewVisibility(visibility: Int) {
        binding.recyclerView.visibility = visibility
        binding.contentStateView.viewGroupContent.visibility = visibility
    }

    private fun setErrorVisibility(visibility: Int, message: String? = null) {
        binding.contentStateView.includeError.apply {
            viewGroupError.visibility = visibility
            tvMessage.text = message
        }
    }
}
