package pl.kapiz.minecraftmapy.data.model

import com.google.gson.annotations.SerializedName
import pl.kapiz.minecraftmapy.utils.trimEnd
import java.io.Serializable

data class Map(
    val id: Long,
    val code: String,
    val url: String,
    val author: Author,
    val info: Info,
    val images: List<String>,
    val stats: Stats
) : Serializable {
    companion object {
        const val SORT_BY_NEWEST = 1
        const val SORT_BY_DISCOVER = 2
    }

    data class Info(
        val title: String,
        val description: String,
        @SerializedName("download_url")
        val downloadUrl: String,
        @SerializedName("created_at")
        val createdAt: String,
        val category: MapCategory,
        @SerializedName("minecraft_version")
        val version: MinecraftVersion
    ) : Serializable {
        val descriptionShort: String get() = description.replace("\n", " ").trimEnd(150)
    }

    data class Stats(
        @SerializedName("download_count")
        val downloadCount: Int,
        @SerializedName("diamond_count")
        val diamondCount: Int,
        @SerializedName("comment_count")
        val commentCount: Int
    ) : Serializable
}
