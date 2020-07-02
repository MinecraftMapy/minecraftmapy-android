package pl.kapiz.minecraftmapy.data.pojo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    val id: Long,
    val info: Info,
    val stats: Stats
) : Serializable {

    data class Info(
        val username: String,
        val description: String
    ) : Serializable

    data class Stats(
        @SerializedName("map_count")
        val mapCount: Int
    ) : Serializable
}
