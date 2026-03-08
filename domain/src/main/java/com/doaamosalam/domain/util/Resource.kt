package com.doaamosalam.domain.util
/**
 * Generic wrapper for UI states: Loading, Success, Error.
 * Used across all layers to communicate data flow states.
 */

sealed class Resource<out T>(
    val data: T? = null,
    val exception: Exception? = null
) {

    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(exception: Exception, data: T? = null) : Resource<T>(data, exception)
}