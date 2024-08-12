package com.posite.koinex.util.network

import com.posite.koinex.KoinApplication
import retrofit2.Response

const val NETWORK_EXCEPTION_OFFLINE_CASE = "network status is offline"
const val NETWORK_EXCEPTION_BODY_IS_NULL = "result body is null"

suspend fun <T : Any, R : Any> handleApi(
    execute: suspend () -> Response<T>,
    mapper: (T) -> R
): DataResult<R> {
    if (KoinApplication.isOnline().not()) {
        return DataResult.Error(Exception(NETWORK_EXCEPTION_OFFLINE_CASE))
    }

    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful) {
            body?.let {
                DataResult.Success(mapper(it))
            } ?: run {
                throw NullPointerException(NETWORK_EXCEPTION_BODY_IS_NULL)
            }
        } else {
            getFailDataResult(body, response)
        }
    } catch (e: Exception) {
        DataResult.Error(e)
    }
}


private fun <T : Any> getFailDataResult(body: T?, response: Response<T>) = body?.let {
    DataResult.Fail(statusCode = response.code(), message = it.toString())
} ?: run {
    DataResult.Fail(statusCode = response.code(), message = response.message())
}