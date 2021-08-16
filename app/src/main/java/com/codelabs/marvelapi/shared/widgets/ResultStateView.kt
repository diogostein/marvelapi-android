package com.codelabs.marvelapi.shared.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.marvelapi.R

class ResultStateView constructor(
        context: Context,
        attrs: AttributeSet?
) : FrameLayout(context, attrs) {
    private val view = inflate(context, R.layout.result_state_view, this)

    val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
    val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
    val tvMessage = view.findViewById<TextView>(R.id.textView)

    private var hasDivider: Boolean = false

    init {
        setLayout(attrs)
    }

    private fun setLayout(attrs: AttributeSet?) {
        attrs?.let { attributeSet ->
            val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.ResultStateView)

            hasDivider = attributes.getBoolean(R.styleable.ResultStateView_has_divider, false)

            attributes.recycle()
        }
    }

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        recyclerView.layoutManager = layoutManager
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        recyclerView.adapter = adapter
    }

    fun addOnScrollListener(onScrolListener: RecyclerView.OnScrollListener) {
        recyclerView.addOnScrollListener(onScrolListener)
    }

    fun showRecyclerView(adapter: RecyclerView.Adapter<*>? = null) {
        recyclerView.visibility = VISIBLE
        progressBar.visibility = GONE
        tvMessage.visibility = GONE
        if (adapter != null) setAdapter(adapter)
    }

    fun showProgressIndicator() {
        recyclerView.visibility = GONE
        progressBar.visibility = VISIBLE
        tvMessage.visibility = GONE
    }

    fun showErrorMessage(message: String? = null) {
        recyclerView.visibility = GONE
        progressBar.visibility = GONE
        tvMessage.visibility = VISIBLE
        tvMessage.text = message
    }
}