package com.codelabs.marvelapi.core

object Const {

    object GridViewPaging {
        const val SPAN_COUNT = 3
        const val PAGE_SIZE = SPAN_COUNT * 10 // Must always be multiple of SPAN_COUNT to avoid crash
    }

    object HorizontalListViewPaging {
        const val PAGE_SIZE = 15
    }

}
