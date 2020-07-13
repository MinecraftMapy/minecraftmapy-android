package pl.kapiz.minecraftmapy.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    val id: Long,
    val url: String,
    val info: Info,
    val stats: Stats
) : Serializable {

    data class Info(
        val username: String,
        val description: String?,
        @SerializedName("last_logged_at")
        val lastLoggedAt: String,
        @SerializedName("last_logged_relative")
        val lastLoggedRelative: String,
        @SerializedName("is_active")
        val isActive: Boolean,
        @SerializedName("avatar_url")
        val avatarUrl: String?,
        val role: String
    ) : Serializable

    data class Stats(
        @SerializedName("diamond_sum")
        val diamondsTotal: Int,
        @SerializedName("download_sum")
        val downloadsTotal: Int,
        @SerializedName("written_comments")
        val commentsWritten: Int,
        @SerializedName("given_diamonds")
        val starsGiven: Int,
        @SerializedName("map_count")
        val mapCount: Int,
        @SerializedName("follower_count")
        val followerCount: Int
    ) : Serializable
}
