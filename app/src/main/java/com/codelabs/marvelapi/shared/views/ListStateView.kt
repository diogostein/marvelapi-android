package com.codelabs.marvelapi.shared.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codelabs.marvelapi.R
import com.codelabs.marvelapi.databinding.ListStateViewBinding

class ListStateView constructor(
    context: Context,
    attrs: AttributeSet?,
) : FrameLayout(context, attrs) {
    private val binding = ListStateViewBinding.inflate(LayoutInflater.from(context), this)

    private var hasDivider: Boolean = false

    init {
        setLayout(attrs)

        binding.swipeRefresh.isEnabled = false
    }

    private fun setLayout(attrs: AttributeSet?) {
        attrs?.let { attributeSet ->
            val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.ListStateView)

            hasDivider = attributes.getBoolean(R.styleable.ListStateView_has_divider, false)

            attributes.recycle()
        }
    }

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        binding.recyclerView.layoutManager = layoutManager
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        binding.recyclerView.adapter = adapter
    }

    fun addOnScrollListener(onScrollListener: RecyclerView.OnScrollListener) {
        binding.recyclerView.addOnScrollListener(onScrollListener)
    }

    fun setOnRetryClickListener(onRetryClickListener: OnClickListener) {
        binding.includeError.btnReload.setOnClickListener(onRetryClickListener)
    }

    fun setOnRefreshListener(onRefreshListener: SwipeRefreshLayout.OnRefreshListener) {
        with (binding.swipeRefresh) {
            isEnabled = true
            setOnRefreshListener(onRefreshListener)
        }
    }

    fun showRecyclerView(adapter: RecyclerView.Adapter<*>? = null) {
        binding.recyclerView.visibility = VISIBLE
        binding.swipeRefresh.isRefreshing = false
        setErrorVisibility(GONE)
        if (adapter != null) setAdapter(adapter)
    }

    fun showProgressIndicator() {
        binding.recyclerView.visibility = GONE
        binding.swipeRefresh.isRefreshing = true
        setErrorVisibility(GONE)
    }

    fun showError(message: String? = null) {
        binding.recyclerView.visibility = GONE
        binding.swipeRefresh.isRefreshing = false
        setErrorVisibility(VISIBLE, message)
    }

    private fun setErrorVisibility(visibility: Int, message: String? = null) {
        binding.includeError.apply {
            viewGroupError.visibility = visibility
            tvMessage.text = message
        }
    }
}