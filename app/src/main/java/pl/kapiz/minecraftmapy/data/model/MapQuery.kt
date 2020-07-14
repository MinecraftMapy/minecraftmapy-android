/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-14.
 */

package pl.kapiz.minecraftmapy.data.model

import pl.kapiz.minecraftmapy.data.model.enum.SortMode
import java.io.Serializable

data class MapQuery(
    val sortBy: SortMode? = null,
    val sortSeed: Int? = null,
    val category: MapCategory? = null,
    val version: MinecraftVersion? = null,
    val queryText: String? = null
) : Serializable
