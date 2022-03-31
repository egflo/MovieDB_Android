package com.app.moviedb_android.data.model

import com.google.gson.JsonArray
import com.google.gson.JsonElement

data class Page<T>(
    val content: List<T>,
    val pageable: Pageable,
    val totalPages: Long,
    val totalElements: Long,
    val last: Boolean,
    val size: Long,
    val number: Long,
    val sort: Sort,
    val numberOfElements: Long,
    val first: Boolean,
    val empty: Boolean
)

data class Pageable (
    val sort: Sort,
    val offset: Long,
    val pageNumber: Long,
    val pageSize: Long,
    val paged: Boolean,
    val unpaged: Boolean
)

data class Sort (
    val sorted: Boolean,
    val unsorted: Boolean,
    val empty: Boolean
)