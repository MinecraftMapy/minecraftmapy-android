package pl.kapiz.minecraftmapy.data.api

import retrofit2.Response

sealed class ApiResponse<T> {
    companion object {
        @Suppress("UNCHECKED_CAST")
        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) ApiEmptyResponse()
                else ApiSuccessResponse(body)
            } else {
                val message = (response as? Response<ApiErrorResponse.Error>)?.body()?.message
                    ?: response.errorBody()?.string() ?: response.message()
                ApiErrorResponse(response.code(), message)
            }
        }

        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(null, error.message ?: "Unknown error.")
        }
    }

    data class ApiErrorResponse<T>(
        val errorCode: Int?,
        val errorMessage: String
    ) : ApiResponse<T>() {
        data class Error(val message: String)
    }

    class ApiEmptyResponse<T> : ApiResponse<T>()
    data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()
}
