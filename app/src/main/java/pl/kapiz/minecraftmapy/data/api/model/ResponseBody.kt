package pl.kapiz.minecraftmapy.data.api.model

import com.google.gson.annotations.SerializedName

data class ResponseBody<T>(
    val data: T,
    @SerializedName("meta")
    val paging: Paging?
) {

    data class Paging(
        val from: Int,
        val to: Int,
        @SerializedName("current_page")
        val currentPage: Int,
        @SerializedName("last_page")
        val lastPage: Int,
        @SerializedName("per_page")
        val perPage: Int,
        val totalCount: Int,
        val path: String
    ) {
        val nextKey: Int?
            get() = when (currentPage) {
                lastPage -> null
                else -> currentPage + 1
            }
    }
}
