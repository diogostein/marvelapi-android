package com.codelabs.marvelapi.shared.views

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.core.content.res.ResourcesCompat
import com.codelabs.marvelapi.R

class MarvelTextView constructor(
    context: Context,
    attrs: AttributeSet?,
) : androidx.appcompat.widget.AppCompatTextView(context, attrs) {

    init {
        setBackgroundResource(R.color.color_secondary)
        setPadding(2.dp, 3.dp, 2.dp, 1.dp)
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 32f)
        isAllCaps = true
        typeface = ResourcesCompat.getFont(context, R.font.marvel_regular)
    }

    private val Int.dp get() = (this * resources.displayMetrics.density).toInt()

}