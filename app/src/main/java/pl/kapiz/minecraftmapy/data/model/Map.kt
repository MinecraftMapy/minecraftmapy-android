package pl.kapiz.minecraftmapy.data.model

import pl.kapiz.minecraftmapy.utils.trimEnd
import java.io.Serializable

data class Map(
    val id: Long,
    val author: Author,
    val info: Info,
    val images: List<String>
) : Serializable {
    companion object {
        const val SORT_BY_NEWEST = 1
        const val SORT_BY_DISCOVER = 2
    }

    data class Author(
        val username: String
    ) : Serializable

    data class Info(
        val title: String,
        val description: String
    ) : Serializable {

        val descriptionShort: String get() = description.replace("\n", " ").trimEnd(150)
    }
}
