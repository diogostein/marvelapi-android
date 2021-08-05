package com.codelabs.marvelapi.shared.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.marvelapi.R

class CustomRecyclerView constructor(
        context: Context,
        attrs: AttributeSet?
) : FrameLayout(context, attrs) {
    private val view = inflate(context, R.layout.custom_recycler_view, this)

    val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
    val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
    val tvMessage = view.findViewById<TextView>(R.id.textView)

    private var hasDivider: Boolean = false

    init {
        setLayout(attrs)
    }

    private fun setLayout(attrs: AttributeSet?) {
        attrs?.let { attributeSet ->
            val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.CustomRecyclerView)

            hasDivider = attributes.getBoolean(R.styleable.CustomRecyclerView_has_divider, false)

            attributes.recycle()
        }
    }

    fun setRecyclerView(adapter: RecyclerView.Adapter<*>? = null) {
        recyclerView.visibility = VISIBLE
        progressBar.visibility = GONE
        tvMessage.visibility = GONE
        if (adapter != null) recyclerView.adapter = adapter
    }

    fun setLoading() {
        recyclerView.visibility = GONE
        progressBar.visibility = VISIBLE
        tvMessage.visibility = GONE
    }

    fun setMessage(message: String? = null) {
        recyclerView.visibility = GONE
        progressBar.visibility = GONE
        tvMessage.visibility = VISIBLE
        tvMessage.text = message
    }
}