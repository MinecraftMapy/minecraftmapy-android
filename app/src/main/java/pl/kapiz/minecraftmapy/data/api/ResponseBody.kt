package pl.kapiz.minecraftmapy.data.api

import com.google.gson.annotations.SerializedName

data class ResponseBody<T>(
    val data: T,
    val meta: Meta
) {

    data class Meta(
        @SerializedName("current_page") val currentPage: Int,
        val from: Int,
        @SerializedName("last_page") val lastPage: Int,
        val path: String,
        @SerializedName("per_page") val perPage: Int,
        val to: Int,
        val total: Int
    )

    val nextKey: Int?
        get() = when (meta.currentPage) {
            meta.lastPage -> null
            else -> meta.currentPage + 1
        }
}
