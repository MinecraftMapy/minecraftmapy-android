/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-14.
 */

package pl.kapiz.minecraftmapy.data.model

import pl.kapiz.minecraftmapy.data.model.enum.SortMode
import java.io.Serializable
import kotlin.random.Random

data class MapQuery(
    val sortBy: SortMode?,
    val sortSeed: Int? = null,
    val category: MapCategory? = null,
    val version: MinecraftVersion? = null,
    val queryText: String? = null
) : Serializable {
    companion object {
        private fun seed() = Random.nextInt(0, 99999)

        fun default() = MapQuery(
            sortBy = SortMode.DISCOVER,
            sortSeed = seed()
        )

        fun withText(queryText: String) = MapQuery(
            sortBy = SortMode.BEST_MATCH,
            queryText = queryText
        )

        fun withCategory(category: MapCategory) = MapQuery(
            sortBy = SortMode.DISCOVER,
            sortSeed = seed(),
            category = category
        )

        fun withVersion(version: MinecraftVersion) = MapQuery(
            sortBy = SortMode.DISCOVER,
            sortSeed = seed(),
            version = version
        )
    }
}
