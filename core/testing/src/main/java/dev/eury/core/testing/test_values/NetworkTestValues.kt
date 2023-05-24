package dev.eury.core.testing.test_values

import dev.eury.core.data.utils.ResultOperation
import dev.eury.core.network.calladapter.NetworkResponse
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import java.net.UnknownHostException

object NetworkTestValues {
    fun mockResponseBody(message: String = "") =
        message.toResponseBody()

    fun mockApiError(
        message: String = "",
        code: Int = 400,
        throwable: Throwable? = null
    ) = NetworkResponse.ApiError(
        mockResponseBody(message),
        code = code,
        throwable = throwable
    )

    fun mockNetworkError(
        exception: IOException = UnknownHostException("")
    ) = NetworkResponse.NetworkError(exception)

    fun mockResultOperationError(
        errorBody: ResponseBody? = mockResponseBody(),
        throwable: Throwable? = null,
        code: Int? = 400,
        isNetworkError: Boolean = false
    ) = ResultOperation.Error(
        body = errorBody,
        throwable = throwable,
        code = code,
        isNetworkError = isNetworkError
    )
}