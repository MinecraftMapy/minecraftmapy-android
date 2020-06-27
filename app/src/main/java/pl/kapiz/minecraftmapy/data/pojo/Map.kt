package pl.kapiz.minecraftmapy.data.pojo

data class Map(
    val id: Long,
    val info: Info
) {
    data class Info(
        val title: String,
        val description: String
    )
}
