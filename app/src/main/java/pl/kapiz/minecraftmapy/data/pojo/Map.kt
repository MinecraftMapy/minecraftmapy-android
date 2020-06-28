package pl.kapiz.minecraftmapy.data.pojo

data class Map(
    val id: Long,
    val info: Info,
    val images: List<String>
) {
    data class Info(
        val title: String,
        val description: String
    )
}
