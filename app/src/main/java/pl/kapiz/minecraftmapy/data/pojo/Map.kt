package pl.kapiz.minecraftmapy.data.pojo

import java.io.Serializable

data class Map(
    val id: Long,
    val info: Info,
    val images: List<String>
) : Serializable {
    companion object {
        const val SORT_BY_NEWEST = 1
        const val SORT_BY_DISCOVER = 2
    }

    data class Info(
        val title: String,
        val description: String
    ) : Serializable
}
