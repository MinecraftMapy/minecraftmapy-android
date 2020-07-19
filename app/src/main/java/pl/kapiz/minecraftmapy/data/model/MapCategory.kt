/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-14.
 */

package pl.kapiz.minecraftmapy.data.model

import java.io.Serializable

data class MapCategory(
    val id: Int,
    val name: String
) : Serializable
