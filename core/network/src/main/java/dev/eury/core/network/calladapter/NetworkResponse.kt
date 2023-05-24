package dev.eury.core.network.calladapter

import okhttp3.ResponseBody
import java.io.IOException

sealed class NetworkResponse<out T : Any> {
    data class Success<out T : Any>(val value: T) : NetworkResponse<T>()

    data class NetworkError(val error: IOException) : NetworkResponse<Nothing>(), BaseNetworkError {
        override val throwableOrDefault: Throwable = error
    }

    data class ApiError(
        val body: ResponseBody?,
        val code: Int? = null,
        val throwable: Throwable? = null
    ) : NetworkResponse<Nothing>(), BaseNetworkError {
        override val throwableOrDefault = throwable ?: Throwable("Api error: $code")
    }

    val optValue
        get() = (this as? Success)?.value

    val asError
        get() = this as? BaseNetworkError

    val isSuccessful
        get() = this is Success

    fun <R : Any> map(mapper: (T) -> R) = when (this) {
        is ApiError -> this
        is NetworkError -> this
        is Success -> Success(mapper(value))
    }

    fun doOnError(block: BaseNetworkError.() -> Unit): NetworkResponse<T> {
        if (this is BaseNetworkError) block()
        return this
    }

    suspend fun doOnSuccess(block: suspend T.() -> Unit): NetworkResponse<T> {
        if (this is Success) block(value)
        return this
    }
}

sealed interface BaseNetworkError {
    val throwableOrDefault: Throwable
}