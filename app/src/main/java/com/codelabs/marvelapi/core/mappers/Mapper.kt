package com.codelabs.marvelapi.core.mappers

abstract class Mapper<I, O> {
    abstract fun map(input: I) : O

    fun map(list: List<I>) : List<O> = list.map { map(it) }
}