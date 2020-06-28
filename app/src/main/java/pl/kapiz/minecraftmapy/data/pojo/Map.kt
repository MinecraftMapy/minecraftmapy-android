package pl.kapiz.minecraftmapy.data.pojo

import java.io.Serializable

data class Map(
    val id: Long,
    val info: Info,
    val images: List<String>
) : Serializable {
    data class Info(
        val title: String,
        val description: String
    ) : Serializable
}
