/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-14.
 */

package pl.kapiz.minecraftmapy.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Comment(
    val id: Long,
    val author: Author,
    val info: Info
) : Serializable {
    data class Info(
        val content: String,
        @SerializedName("created_at")
        val createdAt: String
    ) : Serializable
}
